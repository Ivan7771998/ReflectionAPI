import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainClass {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class testClass = ClassForTests.class;
        start(testClass);
    }

    public static void start(Class testClass) throws InvocationTargetException, IllegalAccessException {

        Method[] methods = testClass.getMethods();

        int sumBef = 0;
        int sumAfter = 0;
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                sumBef++;
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                sumAfter++;
            }
        }

        if (sumBef > 1 || sumAfter > 1) {
            throw new RuntimeException("Методы с анотациями @BeforeSuite и " +
                    "@AfterSuite должны быть в едином экземпляре!");
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                m.invoke(null);
            }
        }

        List<Method> sorted = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.getAnnotation(Test.class) != null) // only use annotated methods
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority())) // sort by value
                .collect(Collectors.toList());

        for (Method m : sorted) {
            if (m.isAnnotationPresent(Test.class)) {
                m.invoke(null);
            }
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                m.invoke(null);
            }
        }
    }
}
