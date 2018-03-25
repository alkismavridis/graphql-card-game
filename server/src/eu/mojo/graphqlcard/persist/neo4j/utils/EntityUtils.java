package eu.mojo.graphqlcard.persist.neo4j.utils;

import org.neo4j.driver.v1.types.Entity;

public class EntityUtils {
    public static String optString(Entity e, String key, String def) {
        try { return e.get(key).asString(); }
        catch (Exception ex) { return def; }
    }

    public static long optLong(Entity e, String key, long def) {
        try { return e.get(key).asLong(); }
        catch (Exception ex) { return def; }
    }

    public static int optInt(Entity e, String key, int def) {
        try { return e.get(key).asInt(); }
        catch (Exception ex) { return def; }
    }
}
