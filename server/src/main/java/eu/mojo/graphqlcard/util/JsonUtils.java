package eu.mojo.graphqlcard.util;

import com.google.gson.JsonObject;

import java.math.BigInteger;

public class JsonUtils {
    public static String optString(JsonObject job, String key, String def) {
        try { return job.get(key).getAsString(); }
        catch (Exception ex) { return def; }
    }

    //region NUMERIC GETTERS
    public static int optInt(JsonObject job, String key, int def) {
        try { return job.get(key).getAsInt(); }
        catch (Exception ex) { return def; }
    }

    public static long optLong(JsonObject job, String key, long def) {
        try { return job.get(key).getAsLong(); }
        catch (Exception ex) { return def; }
    }

    public static BigInteger optBigInteger(JsonObject job, String key, BigInteger def) {
        try { return job.get(key).getAsBigInteger(); }
        catch (Exception ex) { return def; }
    }
    //endregion
}
