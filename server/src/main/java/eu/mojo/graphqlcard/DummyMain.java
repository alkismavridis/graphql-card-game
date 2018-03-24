package eu.mojo.graphqlcard;


import eu.mojo.graphqlcard.core.AppInitializer;
import eu.mojo.graphqlcard.core.Apps;
import eu.mojo.graphqlcard.modules.user.User;

/**
 * This is a dummy main function.
 * The actual application will run
 * inside a framework, like a Servlet or Apache Tomcat*/
public class DummyMain {

    static final String QUERY =
            "MATCH (o) return o;";


    public static void main(String args[]) {
        try {
            AppInitializer.start( System.getProperty("appDataDir") );
            User user = new User("123", "456");
            User user2 = new User("abc", "def");
            Object result = Apps.get().getDB().start()
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
