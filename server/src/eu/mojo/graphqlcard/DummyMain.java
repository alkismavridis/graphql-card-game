package eu.mojo.graphqlcard;


import eu.mojo.graphqlcard.core.App;
import eu.mojo.graphqlcard.core.AppInitializer;
import eu.mojo.graphqlcard.core.user.User;

/**
 * This is a dummy main function.
 * The actual application will run
 * inside a framework, like a GraphqlCardGameServlet or Apache Tomcat*/
public class DummyMain {

    static final String QUERY =
            "MATCH (o) return o;";


    public static void main(String args[]) {
        try {
            AppInitializer.start( System.getProperty("hostAppDir") );
            User user = new User("123", "456");
            User user2 = new User("abc", "def");
            Object result = App.getDB().start()
                    .updateOrInsertUser(user)
                    .updateOrInsertUser(user2)
                    .send();
            System.out.println(result);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
