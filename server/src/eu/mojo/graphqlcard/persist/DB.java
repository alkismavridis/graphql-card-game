package eu.mojo.graphqlcard.persist;

import eu.mojo.graphqlcard.core.user.User;

/**
 * A not thread-safe interface that represents a Persistent-server.
 * Users can freely use this interface to save and load
 * various types of Data.
 *
 * The exact implementation it should be agnostic.
 * This means that the user of this interface should not
 * know if we save in a mySQL database, in files, or in any other way.
 *
 * The user should only know that he can store and retrieve data
 * in a consistent way.
 *
 * transactions are being made by calling start(), then apply a number of
 * changes, like updateOrInsertUser, and finally, calling send().
 *
 * The user can chain those like
 *  myDB.start().updateOrInsertUser(myUser).send(),
 *
 *  or like:
 *      myDB.start();
 *
 *      ...code....
 *      myDB.updateUser(...);
 *     ...code...
 *
 *     myDB.send();   ------   or:   myDB.cancel();  to abort
 *
 *
 *
 *
 * changes are made only when send() is called.
 * Until then, nothing happens.
 * Calling cancel() will cancel all operations
 * since last start().
 *
 * This should not happen, but just in case:
 * calling 2 times start(), without calling send() in between,
 * will erase all content between them.
 * Actually calling start() has simmilar effects to calling cancel()
 *
 *
 */
public interface DB {
    void connect() throws DBException;
    void disconnect() throws DBException;


    /**
     * Starts a transaction, closing any open at this moment.
     * previously open transaction is never commited and all
     * associated resources are being freed.
     * */
    DB start(boolean ensureEmpty);

    /**
     * same as start(true);*/
    default DB start() { return start(true); }

    /**
     * The most important method!
     *
     * You must call send() to save or load data.
     * Then, and only then, the changes are persisted.
     * Before that, the user can cancel() them, and nothing will be affected.
     * */
    DB send() throws DBException;


    /**
     * cancels all changes since last start()*/
    void cancel();


    //region User
    DB loadUser(long id, Wrap<User> wrap) throws DBException;
    DB updateOrInsertUser(User user) throws DBException;
    DB deleteUser(User user) throws DBException;
    //endregion




    //region AUTO_COMMIT
    User getUser(long id) throws DBException;
    //endregion
}
