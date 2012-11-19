JUnitFactory
============

<p>
JUnitFactoryRunner and @JUnitFactory allow you to create JUnit tests dynamically. Like annotation @Factory in TestNG.<br/>
Requires junit 4.10 library.
</p>

#Example

```java

import org.junit.runner.RunWith;
import tomas.JUnitFactoryRunner;
import tomas.JUnitFactoryRunner.JUnitFactory;

@RunWith(JUnitFactoryRunner.class)
public class SomeSuite {

    @JUnitFactory
    public static ATest test1() {
        return new ATest("abc", 2);
    }

    @JUnitFactory
    public static BTest test2() {
        return new BTest();
    }
}

```