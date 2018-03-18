package eu.mojo.graphqlcard;


/**
 * This is a dummy main function.
 * The actual application will run
 * inside a framework, like a Servlet or Apache Tomcat*/
public class DummyMain {
    public static void main(String args[]) {
        try { AppInitializer.start( System.getProperty("appDataDir") ); }
        catch (Exception ex) { ex.printStackTrace(); }
    }
}
