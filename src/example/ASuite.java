package example;

import org.junit.runner.RunWith;
import tomas.JUnitFactoryRunner;
import tomas.JUnitFactoryRunner.JUnitFactory;

@RunWith(JUnitFactoryRunner.class)
public class ASuite {

    @JUnitFactory
    public static ATest test1() {
        return new ATest(new ATest.AFactory() {
            @Override
            public A createA(String firstName) {
                return new A(firstName);
            }
        });
    }
}
