JUnitFactory
============

<p>
JUnitFactoryRunner and @JUnitFactory allow you to create JUnit tests dynamically. Like annotation @Factory in TestNG.<br/>
Requires junit 4.10 library.
</p>
<p>
<b>Example:</b>
</p>
<code>
import org.junit.runner.RunWith;<br/>
import tomas.JUnitFactoryRunner;<br/>
import tomas.JUnitFactoryRunner.JUnitFactory;<br/>

&#064;RunWith(JUnitFactoryRunner.class)
public class SomeSuite {

    &#064;JUnitFactory
    public static ATest test1() {
        return new ATest("abc", 2);
    }

    &#064;JUnitFactory
    public static BTest test2() {
        return new BTest();
    }
}
<code/>