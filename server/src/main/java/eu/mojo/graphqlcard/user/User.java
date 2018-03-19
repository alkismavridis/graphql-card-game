package eu.mojo.graphqlcard.user;

/**
 * <h1>Basic User Entity</h1>
 * @author  RojoMojo
 * @version 1.0

 */



public class User {
    //region fields
    private String username;
    private String password;
    //endregion


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //region getter and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion
}
