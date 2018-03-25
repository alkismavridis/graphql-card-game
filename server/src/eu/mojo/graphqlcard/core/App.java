package eu.mojo.graphqlcard.core;

import eu.mojo.graphqlcard.core.graphql.GraphqlService;
import eu.mojo.graphqlcard.core.user.UserService;
import eu.mojo.graphqlcard.persist.DB;


/**
 * This is the base of the App hierarchy.
 * It contains references to all application Scoped services
 */
public class App {
    //region FIELDS
    static Config config;
    static DB db;

    private static UserService userService;
    private static GraphqlService graphqlService;
    //endregion


    public static void init(Config c, DB db) {
        App.config = c;
        App.db = db;
    }



    //region GETTERS
    public static Config getConfig() { return config; }
    public static DB getDB() { return db; }

    public static UserService getUserService() { return userService; }
    public static GraphqlService getGraphqlService() { return graphqlService; }
    //endregion
}
