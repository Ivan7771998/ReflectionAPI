public class ClassForTests {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("hello BeforeSuite");
    }

    @Test(priority = 45)
    public static void test45() {
        System.out.println("hello test45");
    }

    @Test(priority = 23)
    public static void test23() {
        System.out.println("hello test23");
    }

    @Test(priority = 1)
    public static void test1() {
        System.out.println("hello test1");
    }

    @Test(priority = 2)
    public static void test2() {
        System.out.println("hello test2");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("hello afterSuite");
    }

}
