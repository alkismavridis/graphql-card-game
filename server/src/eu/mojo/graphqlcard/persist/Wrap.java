package eu.mojo.graphqlcard.persist;

public class Wrap<T> {
    public T value;

    public Wrap() {}
    public Wrap(T val) { this.value = val; }
}
