package eu.mojo.graphqlcard.persist.neo4j;

import eu.mojo.graphqlcard.core.user.User;
import eu.mojo.graphqlcard.persist.Wrap;
import eu.mojo.graphqlcard.persist.neo4j.utils.EntityUtils;
import eu.mojo.graphqlcard.persist.neo4j.utils.Params;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.types.Node;

public class UserDao {
    public static final String CREATE = "CREATE (o:User {username:{un}, password:{pass}}) return ID(o);";
    public static final String GET_BY_ID = "MATCH (o:User) WHERE ID(o)={id} return o;";
    public static final String UPDATE = "MATCH(o:User) WHERE ID(o)=45 SET o.username = {un}, o.password = {pass};";
    public static final String DELETE = "MATCH(o:User) WHERE ID(o)=45 DELETE o;";



    //region UTILS
    private static User makeUser(Node node) {
        User ret = new User(
                EntityUtils.optString(node, "username", null),
                EntityUtils.optString(node, "password", null)
        );

        ret.setId(node.id());
        return ret;
    }
    //endregion




    //region CREATE
    public static Neo4JDB create(User user, Neo4JDB db) {
        StatementResult res = db.transaction.run(CREATE, Params.make("un", user.getUsername(), "pass", user.getPassword()));
        db.addCallback(() -> {
            System.out.println("I create user!");
            user.setId( res.next().get(0).asLong() );
        });

        return db;
    }
    //endregion



    //region READ
    public static User get(long id, Neo4JDB db) {
        System.out.println("I get a user!");
        StatementResult res = db.runSingle(GET_BY_ID, Params.make("id", id));
        Node userNode = res.next().get(0).asNode();
        return makeUser(userNode);
    }

    public static Neo4JDB load(long id, Wrap<User> wrap, Neo4JDB db) {
        StatementResult res = db.transaction.run(GET_BY_ID, Params.make("id", id));
        db.addCallback(() -> {
            Node userNode = res.next().get(0).asNode();
            wrap.value = makeUser(userNode);
            System.out.println("I loaded a user!");
        });

        return db;
    }
    //endregion



    //region UPDATE
    public static Neo4JDB update(User user, Neo4JDB db) {
        StatementResult res = db.transaction.run(UPDATE, Params.make("un", user.getUsername(), "pass", user.getPassword()));
        db.addCallback(() -> {
            System.out.println("I update user!");
            //user.setId( res.next().get(0).asNode().id() );
        });

        return db;
    }
    //endregion



    //region DELETE
    public static Neo4JDB delete(User user, Neo4JDB db) {
        StatementResult res = db.transaction.run(DELETE, Params.make("id", user.getId()));
        db.addCallback(() -> {
            System.out.println("I deleted user!");
            user.setId(0);
        });

        return db;
    }
    //endregion


}
