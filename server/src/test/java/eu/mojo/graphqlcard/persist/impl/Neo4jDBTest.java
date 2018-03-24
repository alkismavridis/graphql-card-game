package eu.mojo.graphqlcard.persist.impl;

import org.junit.*;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.types.Node;

import static org.junit.Assert.*;

public class Neo4jDBTest {
    static Neo4JDB db = new Neo4JDB("bolt://localhost:7687", "neo4j", "???", "TestApp");

    //region SETUP AND CLEAN
    @BeforeClass
    public static void beforeAll() throws Exception {
        db.connect();
    }

    @AfterClass
    public static void afterAll() throws Exception {
        db.disconnect();
    }

    @Before
    public void beforeEvery() {

    }

    @After
    public void afterEvery() {

    }
    //endregion


    @Test
    public void testRunSingle() throws Exception {
        //we create a random number to test matching
        long randomID = Math.round(Math.random() * 100000);

        //we create a node
        StatementResult res = db.runSingle("CREATE (o:DeleteMe {test:"+randomID+"}) return o;");
        assertNotNull(res);

        //we retrieve it and make assertions
        res = db.runSingle("MATCH (o:DeleteMe {test:"+randomID+"}) return o;");
        assertNotNull(res);
        assertTrue(res.hasNext());
        Node node = res.next().get(0).asNode();
        assertNotNull(node);
        assertTrue(node.hasLabel("DeleteMe"));
        assertTrue(node.containsKey("test"));
        assertEquals(randomID, node.get("test").asLong());

        //we delete it
        res = db.runSingle("MATCH (o:DeleteMe) delete o;");
        assertNotNull(res);

        //we test that it is really deleted
        res = db.runSingle("MATCH (o:DeleteMe) return o;");
        assertFalse(res.hasNext());
    }
}
