package eu.mojo.graphqlcard.persist;

public enum DBError {
    USER_ALREADY_EXISTS(1);


    private int value;
    DBError(int v) { this.value = v; }

    public int getValue() { return value; }
}
