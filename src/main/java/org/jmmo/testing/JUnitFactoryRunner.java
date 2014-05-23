package org.jmmo.testing;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Run test contains public static method annotated with {@link JUnitFactory} annotation.
 * The annotated method must returns {@link Iterable} of tests instances.
 * It is important to understand that tests will be created before execution @BeforeClass methods.
 *
 * @see <a href="https://github.com/Megaprog/JUnitFactory">JUnitFactory project on GitHub</a>
 *
 * @author Tomas Shestakov
 */
public class JUnitFactoryRunner extends ParentRunner<Runner> {

    /**
     * Annotation for a factory method which returns test instance.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface JUnitFactory {
    }

    /**
     * Only called reflectively. Do not use programmatically.
     */
    public JUnitFactoryRunner(Class<?> clazz) throws Throwable {
        super(clazz);
        for (FrameworkMethod each : getFactoryMethods(getTestClass())) {
            getChildren().add(new TestClassRunner(each.getMethod()));
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

    protected Iterable<FrameworkMethod> getFactoryMethods(TestClass testClass) throws Exception {
        List<FrameworkMethod> methods = testClass.getAnnotatedMethods(JUnitFactory.class);
        for (FrameworkMethod each : methods) {
            //check method modifiers
            Method method = each.getMethod();
            int modifiers = method.getModifiers();
            if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
                throw new Exception("No public static factory method " + each.getName() + " on class " + testClass.getName());
            }
            //check method return type
            Class<?> returnType = method.getReturnType();
            if (returnType.equals(void.class) || returnType.equals(Void.class)) {
                throw new Exception("Method " + each.getName() + " annotated with JUnitFactory annotation must has something to return on class " + testClass.getName());
            }
            //check method parameters
            if (method.getParameterTypes().length > 0) {
                throw new Exception("Method " + each.getName() + " annotated with JUnitFactory annotation must has no parameters on class " + testClass.getName());
            }
        }
        return methods;
    }

    private class TestClassRunner extends BlockJUnit4ClassRunner {
        private final Method createTestMethod;

        TestClassRunner(Method createTestMethod) throws InitializationError {
            super(createTestMethod.getReturnType());
            this.createTestMethod = createTestMethod;
        }

        @Override
        public Object createTest() throws Exception {
            return createTestMethod.invoke(null);
        }

        @Override
        protected void validateConstructor(List<Throwable> errors) {}

        @Override
        protected Annotation[] getRunnerAnnotations() {
            return new Annotation[0];
        }
    }

}
