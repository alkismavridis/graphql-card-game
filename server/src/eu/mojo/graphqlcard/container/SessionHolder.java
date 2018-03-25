package eu.mojo.graphqlcard.container;


import eu.mojo.graphqlcard.core.user.User;

import java.util.HashMap;
import java.util.Map;

public class SessionHolder {
    //region FIELDS
    Map<String, AppSession> sessions;
    //endregion


    SessionHolder() {
        sessions = new HashMap<>(15);
    }

    //region GETTERS
    public AppSession get(String id) { return sessions.get(id); }
    //endregion


    //region MODIFIERS
    /**
     * This method will create a session, overwriting any existing with this id
     * */
    public AppSession create(String id, User user) {
        AppSession newSession = new AppSession(id, user);
        sessions.put(id, newSession);
        return newSession;
    }

    /**
     * This method will create get a session, if one exists, and will create a new one (and return it), otherwise.
     * */
    public AppSession getOrCreate(String id, User user) {
        AppSession newSession = new AppSession(id, user);
        sessions.put(id, newSession);
        return newSession;
    }

    /**
     * Deletes a session, if it exists, and returns it.
     * @return the deleted session. null if no sesison was found
     * */
    public AppSession delete(String id) {
        return sessions.remove(id);
    }
    //endregion
}
