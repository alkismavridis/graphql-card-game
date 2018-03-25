package eu.mojo.graphqlcard.core;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.mojo.graphqlcard.persist.DB;
import eu.mojo.graphqlcard.persist.neo4j.Neo4JDB;

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
        DB db = new Neo4JDB(config.getDbURI(), config.getDbUsername(),  config.getDbPassword(), config.getDbRootNode());
        db.connect();

        //and finally...
        App.init(config, db);
    }

    public static void stop() throws Exception {
        App.getDB().disconnect();
    }

    //endregion
}
