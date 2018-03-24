package eu.mojo.graphqlcard.persist.impl;

import eu.mojo.graphqlcard.modules.user.User;
import eu.mojo.graphqlcard.persist.DB;
import eu.mojo.graphqlcard.persist.DBException;
import eu.mojo.graphqlcard.persist.Wrap;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

public class Neo4JDB implements DB {
    //region FIELDS
    //connection variables
    private String dbURL;
    private String username;
    private String password;
    private Driver driver;

    Session session;
    Transaction transaction;
    String rootLabel;

    /**
     * we will push here all operations that will be executed when the transaction is done.
     * Autocommit transactions dont need to be pushed, because the operation is done immediatelly.
     * */
    private List<Runnable> callbacks = new ArrayList<>(20);
    //endregion




    //region LIFE CYCLE
    public Neo4JDB(String dbURL, String username, String password, String rootLabel) {
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
        this.rootLabel = rootLabel;

        this.driver = GraphDatabase.driver(
                dbURL,
                AuthTokens.basic(username, password));


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



    //region UTIL FUNCTIONS
    StatementResult runSingle(String str) {
        transaction.close();
        StatementResult ret = session.run(str);
        return ret;
    }
    //endregion


    //region User
    @Override
    public DB loadUser(long id, Wrap<User> wrap) {
        wrap.value = null;
        StatementResult res = transaction.run("");
        callbacks.add(() -> wrap.value = res==null? null : new User("", ""));
        return this;
    }

    @Override
    public DB updateOrInsertUser(User user) throws DBException {
        StatementResult res = runSingle("CREATE (o:User {username:\"hello\", password:\"123\"}) return o;");
        callbacks.add(() -> {
            System.out.println("I am here!");
            user.setId( res.next().get(0).asNode().id() );
        });
        return this;
    }
    //endregion



    //region AUTO_COMMIT
    @Override
    public User getUser(long id) throws DBException {
        StatementResult res = runSingle("MATCH (o) return o;");
        return null; //TODO
    }
    //endregion
}
