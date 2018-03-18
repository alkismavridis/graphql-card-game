package eu.mojo.graphqlcard;

import com.google.gson.*;
import org.neo4j.driver.v1.Driver;


/**
 * This is the base of the App hierarchy.
 * It contains references to all application Scoped
 */
public class App {
    //region FIELDS
    private Config config;
    private Driver dbDriver;
    //endregion


    public App(Config c, Driver driver) {
        this.config = c;
        this.dbDriver = driver;
    }


    //region GETTERS

    public Config getConfig() { return config; }
    public Driver getDbDriver() { return dbDriver; }


    //endregion
}
