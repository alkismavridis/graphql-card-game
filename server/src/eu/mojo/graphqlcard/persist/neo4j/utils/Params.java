package eu.mojo.graphqlcard.persist.neo4j.utils;

import java.util.HashMap;
import java.util.Map;

public class Params {
    public static Map<String, Object> make(Object... obj) {
        Map<String,Object> ret = new HashMap<>(obj.length/2);
        for (int i=0, len=obj.length; i<len; i+=2) {
            ret.put( (String)obj[i], obj[i+1]);
        }

        return ret;
    }
}