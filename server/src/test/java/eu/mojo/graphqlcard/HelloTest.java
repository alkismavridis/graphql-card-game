package eu.mojo.graphqlcard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloTest {

    @Test
    public void helloTest() {
        assertEquals(55, Hello.testMethod());
    }
}
