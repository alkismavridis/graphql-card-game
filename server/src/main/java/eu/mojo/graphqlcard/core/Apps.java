package eu.mojo.graphqlcard.core;


/**
 * This static container represents that whole state of this
 * JVM instance.
 *
 * It contains an App object.
 */
public class Apps {
    private static App instance;
    public static App get() { return instance; }
    public static void setInstance(App inst) { instance = inst; }
}
