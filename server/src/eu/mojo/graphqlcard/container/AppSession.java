package eu.mojo.graphqlcard.container;

import eu.mojo.graphqlcard.core.user.User;

public class AppSession {
    String id;
    User user;

    public AppSession(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public User getUser() { return user; }
}
