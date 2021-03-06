package eu.mojo.graphqlcard.core;


import com.google.gson.JsonObject;
import eu.mojo.graphqlcard.util.JsonUtils;

import java.nio.file.Path;

/**
 * This object will be initialized when the all starts
 * It contains configuration options, that will be provided
 * by a json file.
 */
public class Config {
    //region FIELDS
    private Path appDataBase;

    private String dbURI;
    private String dbUsername;
    private String dbPassword;
    private String dbRootNode;
    //endregion


    public Config(JsonObject job, Path appDataBase) {
        this.appDataBase = appDataBase;
        dbURI = JsonUtils.optString(job, "dbURI", "123");
        dbUsername = JsonUtils.optString(job, "dbUsername", "neo4j");
        dbPassword = JsonUtils.optString(job, "dbPassword", "password");
        dbRootNode = JsonUtils.optString(job, "dbRootNode", "App");
    }


    //region GETTERS

    public Path getHostAppDir() { return appDataBase; }
    public String getDbURI() { return dbURI; }
    public String getDbUsername() { return dbUsername; }
    public String getDbPassword() { return dbPassword; }
    public String getDbRootNode() { return dbRootNode; }

    //endregion
}
