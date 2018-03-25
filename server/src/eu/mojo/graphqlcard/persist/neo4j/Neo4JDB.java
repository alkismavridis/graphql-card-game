package eu.mojo.graphqlcard.persist.neo4j;

import eu.mojo.graphqlcard.core.user.User;
import eu.mojo.graphqlcard.persist.DB;
import eu.mojo.graphqlcard.persist.DBException;
import eu.mojo.graphqlcard.persist.Wrap;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Neo4JDB implements DB {
    //region FIELDS
    //connection variables
    private String dbURL;
    private String username;
    private String password;
    private Driver driver;

    private Session session;
    Transaction transaction;
    String rootLabel;

    /**
     * we will push here all operations that will be executed when the transaction is done.
     * Autocommit transactions don't need to be pushed, because the operation is done immediatelly.
     * */
    private List<Runnable> callbacks = new ArrayList<>(20);
    //endregion




    //region LIFE CYCLE
    public Neo4JDB(String dbURL, String username, String password, String rootLabel) {
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
        this.rootLabel = rootLabel;

        Config conf = Config
                .build()
                .withConnectionTimeout(2, TimeUnit.MINUTES)
                .toConfig();

        this.driver = GraphDatabase.driver(
                dbURL,
                AuthTokens.basic(username, password),
                conf);


        session = driver.session();
        transaction = session.beginTransaction();
    }

    @Override
    public void connect() throws DBException {
        //nothing to do
        DBInitializer.initialize(this);
    }

    @Override
    public void disconnect() {
        session.close();
        driver.close();
    }
    //endregion




    //region TRANSACTION LIFE CYCLE
    @Override
    public DB start(boolean ensureEmpty) {
        if (ensureEmpty) {
            cancel();
            transaction.close();
            transaction = session.beginTransaction();
            callbacks.clear();
        }
        return this;
    }

    @Override
    public DB send() {
        //send transaction
        transaction.success();
        transaction.close();

        //execute all scheduled operations
        for (Runnable callback : callbacks) callback.run();
        callbacks.clear();

        //done
        return this;
    }

    @Override
    public void cancel() {
        transaction.close();
        callbacks.clear();
    }
    //endregion



    //region RUNNERS
    StatementResult runSingle(String str) {
        transaction.close();
        StatementResult ret = session.run(str);
        return ret;
    }

    StatementResult runSingle(String str, Map<String, Object> params) {
        transaction.close();
        StatementResult ret = session.run(str, params);
        return ret;
    }

    void addCallback(Runnable r) { callbacks.add(r); }
    //endregion





    //region User CHAINING
    @Override
    public User getUser(long id) throws DBException {
        return UserDao.get(id, this);
    }

    @Override
    public DB loadUser(long id, Wrap<User> wrap) throws DBException {
        return UserDao.load(id, wrap, this);
    }

    @Override
    public DB updateOrInsertUser(User user) throws DBException {
        if(user.getId()>0) return UserDao.update(user, this);
        else return UserDao.create(user, this);
    }

    @Override
    public DB deleteUser(User user) throws DBException {
        return UserDao.delete(user, this);
    }
    //endregion
}
