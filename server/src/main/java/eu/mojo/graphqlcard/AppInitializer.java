package eu.mojo.graphqlcard;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class should be called by "main",
 * or by any framework that would encapsulate
 * graphql-card game.
 *
 * After start() is called, an App object can be accessed.
 * */
public class AppInitializer {
    private static App app;

    //region LIFE CYCLE
    public static void start(String dataDirstr) throws Exception {
        //Our mission is to create App object.

        // We must first generate a configuration file for that, which is inside the App's Data Directory.
        //The exact location of the data directory is given by a runtime property, so lets get it!
        Path appDataDir = FileSystems.getDefault().getPath( dataDirstr );
        if (!Files.exists(appDataDir)) throw new Exception("Application Data Directory not found. Abort.");

        //inside this directory there must be the config.json file. We read it:
        Path configJson = appDataDir.resolve("config.json");
        JsonObject job =
                new JsonParser()
                .parse( Files.newBufferedReader(configJson))
                .getAsJsonObject();


        //Great! Now with those 2 in hand, we can generate a configuration object:
        Config config = new Config(job, appDataDir);

        //App needs a db Driver too...
        Driver driver = GraphDatabase.driver(
                config.getDbURI(),
                AuthTokens.basic(config.getDbUsername(), config.getDbPassword()));

        //and finally...
        app = new App(config, driver);
    }

    public static void stop() throws Exception {
        app.getDbDriver().close();
    }

    //endregion


    public static App getApp() { return app; }
}
