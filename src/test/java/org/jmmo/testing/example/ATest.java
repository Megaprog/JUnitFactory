package org.jmmo.testing.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ATest {

    public interface AFactory {
        A createA(String firstName);
    }

    private AFactory aFactory;

    public ATest(AFactory aFactory) {
        this.aFactory = aFactory;
    }

    @Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][] {{ new AFactory() {
            @Override
            public A createA(String firstName) {
                return new A(firstName);
            }
        } }} );
    }

    @Test
    public void testGetFirstName() throws Exception {
        A a = aFactory.createA("Tomas");
        Assert.assertEquals("Tomas", a.getFirstName());
    }
}
