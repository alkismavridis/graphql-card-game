package eu.mojo.graphqlcard.core.user;

/**
 * <h1>Basic User Entity</h1>
 * @author  RojoMojo
 * @version 1.0

 */



public class User {
    //region fields
    long id = 0;
    String username;
    String password;
    //endregion


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //region getter and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //endregion
}
