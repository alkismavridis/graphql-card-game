package eu.mojo.graphqlcard.core;

import eu.mojo.graphqlcard.persist.DB;


/**
 * This is the base of the App hierarchy.
 * It contains references to all application Scoped services
 */
public class App {
    //region FIELDS
    private Config config;
    private DB db;
    //endregion


    public App(Config c, DB db) {
        this.config = c;
        this.db = db;
    }



    //region GETTERS
    public Config getConfig() { return config; }
    public DB getDB() { return db; }


    //endregion
}
