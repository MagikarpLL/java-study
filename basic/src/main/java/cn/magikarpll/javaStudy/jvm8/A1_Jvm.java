package cn.magikarpll.javaStudy.jvm8;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class A1_Jvm {
}

/**
 *VM argument: -Xms200m -Xmx8000m
 *
 * 可见在jdk8中：
 * 1.字符串常量由永久代转移到堆中
 *
 */
class StringOomMock {
    static String base = "string";

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<String>();
        System.out.println("wait");
        Thread.sleep(5000);
        System.out.println("start");
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = base + base;
            System.out.println(i+ ": String length : " + str.length());
            base = str;
            Thread.sleep(1000);
            list.add(str.intern());
        }
    }
}

/**
 *
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=80m
 *
 * @author diandian.zhang
 * @ClassName:OOMTest
 */
class OOMTest {
    public static void main(String[] args) {
        try {
            URL url = new File("F:/Workspace/java/idea/java-study/basic/src/main/java/cn/magikarpll/javaStudy/basic/A21_Jdk8").toURI().toURL();
            URL[] urls = {url};
            ClassLoadingMXBean loadingBean = ManagementFactory.getClassLoadingMXBean();
            List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
            while (true) {
                ClassLoader classLoader = new URLClassLoader(urls);
                classLoaders.add(classLoader);
                classLoader.loadClass("A21_Jdk8");
                System.out.println("total: " + loadingBean.getTotalLoadedClassCount());
                System.out.println("active: " + loadingBean.getLoadedClassCount());
                System.out.println("unloaded: " + loadingBean.getUnloadedClassCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TestClint{

    static {
        i = 100;
    }
    private static int i = 10;

    public void out(){
        System.out.println("i: " + i);
    }

}

//class TestTType{
//
//   public static String method(List<String> list){
//       System.out.println("invoke method List<String>");
//       return "";
//   }
//
//    public static int method(List<Integer> list){
//        System.out.println("invoke method List<Integer>");
//        return 0;
//    }
//
//    public static void main(String[] args){}
//    method()
//
//}

class TestIntSugar{
    Integer a = 1;
    Integer b = 2;
    Integer c = 3;
    Integer d = 3;
    Integer e = 321;
    Integer f = 321;
    Long g = 3L;

    /**
     *  == 在不遇到算数运算的情况下不会自动拆箱
     *  而equals是不会处理数据转型的关系的
     */
    void mehtod(){
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g == (a+b));
        System.out.println(g.equals(a+b));
    }

    public static void main(String[] args) {
        new TestIntSugar().mehtod();
    }


}