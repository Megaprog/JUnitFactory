package org.jmmo.testing.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class BTest {

    public interface BFactory {
        B createB(String firstName, String secondName);
    }

    private BFactory bFactory;

    public BTest(BFactory bFactory) {
        this.bFactory = bFactory;
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{{new BFactory() {
            @Override
            public B createB(String firstName, String secondName) {
                return new B(firstName, secondName);
            }
        } }});
    }


    @Test
    public void testGetSecondName() throws Exception {
        B b = bFactory.createB("Tomas", "Shestakov");
        Assert.assertEquals("Shestakov", b.getSecondName());
    }
}
