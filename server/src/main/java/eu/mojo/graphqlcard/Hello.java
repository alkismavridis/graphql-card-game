package eu.mojo.graphqlcard;

import com.google.gson.*;

public class Hello {

    //region STATIC
    public static void main(String[] args) {
        System.out.println("Hello from java 9!\n\nSincerely yours,\nMojakia");
    }


    public static int testMethod() {
        return new JsonParser().parse("55").getAsInt();
    }
    //endregion
}
