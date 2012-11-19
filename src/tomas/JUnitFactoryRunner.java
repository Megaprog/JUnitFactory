package tomas;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import java.lang.annotation.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Run test contains public static method annotated with {@link JUnitFactory} annotation.
 * The annotated method must returns {@link Iterable} of tests instances.
 * It is important to understand that tests will be created before execution @BeforeClass methods.
 *
 * @author Tomas Shestakov
 */
public class JUnitFactoryRunner extends ParentRunner<Runner> {

    /**
     * Annotation for a factory method which provides {@link Iterable} of of tests instances.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface JUnitFactory {
    }

    /**
     * Only called reflectively. Do not use programmatically.
     */
    public JUnitFactoryRunner(Class<?> klass) throws Throwable {
        super(klass);
        Iterable tests = (Iterable) getFactoryMethod(getTestClass()).invokeExplosively(null);
        Iterator iterator = tests.iterator();
        while (iterator.hasNext()) {
            getChildren().add(new TestClassRunner(iterator.next()));
        }
    }

    private final ArrayList<Runner> runners = new ArrayList<Runner>();

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    @Override
    protected Description describeChild(Runner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(Runner child, RunNotifier notifier) {
        child.run(notifier);
    }

    protected FrameworkMethod getFactoryMethod(TestClass testClass) throws Exception {
        List<FrameworkMethod> methods = testClass.getAnnotatedMethods(JUnitFactory.class);
        if (methods.isEmpty() || methods.size() > 1) {
            throw new Exception("Must be one and only one method annotated with JUnitFactory annotation in class " + testClass.getName());
        }
        int modifiers = methods.get(0).getMethod().getModifiers();
        if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
            throw new Exception("No public static factory method on class " + testClass.getName());
        }
        return methods.get(0);
    }

    private class TestClassRunner extends BlockJUnit4ClassRunner {
        private final Object test;

        TestClassRunner(Object test) throws InitializationError {
            super(test.getClass());
            this.test = test;
        }

        @Override
        public Object createTest() throws Exception {
            return test;
        }

        @Override
        protected void validateConstructor(List<Throwable> errors) {}

        @Override
        protected Annotation[] getRunnerAnnotations() {
            return new Annotation[0];
        }

    }

}
