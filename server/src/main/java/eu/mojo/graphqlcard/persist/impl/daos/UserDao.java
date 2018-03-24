package eu.mojo.graphqlcard.persist.impl.daos;

public class UserDao {
    public static final String CREATE = "CREATE (o:User {username:\"hello\", password:\"123\"}) return o;";
}
