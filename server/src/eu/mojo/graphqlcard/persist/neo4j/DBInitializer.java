package eu.mojo.graphqlcard.persist.neo4j;

import org.neo4j.driver.v1.StatementResult;

public class DBInitializer {
    private static final String INIT_STR =
            "CREATE (o:%s {version:1})  return o;";
            //"CREATE CONSTRAINT ON (user:User) ASSERT user.id IS UNIQUE";

    /**
     * This method will make sure that a Root node is present,
     * it will create it otherwise.
     * */
    static void initialize(Neo4JDB db) {
        StatementResult res = db.runSingle("MATCH (o:"+db.rootLabel+") return o;");
        if (res.hasNext()) return;

        db.runSingle(String.format(INIT_STR, db.rootLabel));
    }
}
