#JUnitFactory

<p>
JUnitFactoryRunner and @JUnitFactory allow you to create JUnit tests dynamically how the annotation @Factory in TestNG does.
</p>

## How to get it?

You can download the latest build at:
    https://github.com/Megaprog/JUnitFactory/releases

Or use it as a maven dependency:

```xml
<dependency>
    <groupId>org.jmmo</groupId>
    <artifactId>junit-factory</artifactId>
    <version>1.1</version>
    <scope>test</scope>
</dependency>
```

## How to use it?

```java
import org.junit.runner.RunWith;
import org.jmmo.testing.JUnitFactoryRunner;
import org.jmmo.testing.JUnitFactoryRunner.JUnitFactory;

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