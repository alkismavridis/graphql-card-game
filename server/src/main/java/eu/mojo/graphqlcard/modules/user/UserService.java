package eu.mojo.graphqlcard.modules.user;

import eu.mojo.graphqlcard.core.Apps;
import eu.mojo.graphqlcard.persist.DB;
import eu.mojo.graphqlcard.persist.DBException;
import eu.mojo.graphqlcard.persist.Wrap;

import java.util.HashMap;
import java.util.Map;


/**
 * This class provides
 * */
public class UserService {
    //region FIELDS
    Map<Long, User> users = new HashMap(100);
    DB db = Apps.get().getDB();
    //endregion




    //region LIFE CYCLE
    //endregion



    //region GETTERS
    public User get(long id) throws DBException {
        User user = users.get(id);
        if (user!=null) return user;
        Wrap<User> wrap1 = new Wrap<>();
        Wrap<User> wrap2 = new Wrap<>();

        db.start().loadUser(1, wrap1).loadUser(2, wrap2).send();
        return user;
    }
    //endregion


    //region UPDATES AND CREATES
    /**
     * This function saves a user into DB and pushes it into the holder.
     * */
    public void updateOrInsers(User user) throws DBException {
        db.updateOrInsertUser(user);
        users.put(user.getId(), user);
    }
    //endregion
}
