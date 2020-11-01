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
 * 测试字符串变量
 *VM argument: -Xms200m -Xmx8000m
 *
 * 可见在jdk8中：
 * 1.字符串常量由永久代转移到堆中。
 * 2.持久代已不存在，PermSize MaxPermSize参数已移除。（看图中最后两行）
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
 * @Description:模拟类加载溢出（元空间oom）
 * @date 年月日上午::
 */
class OOMTest {
    public static void main(String[] args) {
        try {
            //准备url
            URL url = new File("F:/Workspace/java/idea/java-study/basic/src/main/java/cn/magikarpll/javaStudy/basic/A21_Jdk8").toURI().toURL();
            URL[] urls = {url};
            //获取有关类型加载的JMX接口
            ClassLoadingMXBean loadingBean = ManagementFactory.getClassLoadingMXBean();
            //用于缓存类加载器
            List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
            while (true) {
                //加载类型并缓存类加载器实例
                ClassLoader classLoader = new URLClassLoader(urls);
                classLoaders.add(classLoader);
                classLoader.loadClass("A21_Jdk8");
                //显示数量信息（共加载过的类型数目，当前还有效的类型数目，已经被卸载的类型数目）
                System.out.println("total: " + loadingBean.getTotalLoadedClassCount());
                System.out.println("active: " + loadingBean.getLoadedClassCount());
                System.out.println("unloaded: " + loadingBean.getUnloadedClassCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}