package cn.magikarpll.javaStudy.currency;

import java.util.Random;
import java.util.concurrent.RunnableFuture;

public class A1_Volatile {

    public static void main(String[] args) {
        //测试状态量
        testFlag();
    }

    //测试状态量
    public static void testFlag(){
        new Thread(new FlagVolatile()).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("other thread: stop!!!!!!!!!!!!!!!!!!");
                    FlagVolatile.setFlag(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


/**
 * volatile在状态量上的应用
 */
class FlagVolatile implements Runnable{

    private static volatile boolean flag = true;
    private static int value = 0;

    public static void setFlag(boolean value){
        flag = value;
    }

    @Override
    public void run() {
        System.out.println("start run");
        while (flag){
            System.out.println("thread run ,value is " + value++);
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end run");
    }
}

/**
 * 双重检查饿汉式单例模式，这也是单例模式的某种写法
 */
class DoubleCheck{

    //这里用volatile来保证读写的原子性
    private static volatile DoubleCheck instance = null;

    public DoubleCheck(){
        System.out.println("init");
    }

    public DoubleCheck getInstance(){
        if(null == instance){   //外面这个检查主要是为了防止线程都阻塞在synchronized这里，提高效率
            synchronized (DoubleCheck.class){
                if(null == instance){ //这个是为了防止线程进来后，重复new 实例，两个检查缺一不可
                    instance = new DoubleCheck();
                }
            }
        }
        return instance;
    }

}

/**
 * violate常见误区使用方式
 */
class ViolateError{

    private static volatile int count = 0;

    public static void addCount(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ViolateError.addCount();
                }
            }).start();
        }
        System.out.println(count);
    }

}