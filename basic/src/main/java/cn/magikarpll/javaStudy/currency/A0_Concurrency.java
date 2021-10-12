package cn.magikarpll.javaStudy.currency;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 多线程的几种实现方式
 */
public class A0_Concurrency {

    /**
     * 多线程的第一种写法，方便传变量
     */
    public static void threadExtend(){
        new ThreadExtend("A").start();
        new ThreadExtend("B").start();
        new ThreadExtend("C").start();
    }

    /**
     * 多线程的第二种写法，方便多实现
     */
    public static void runnableImplement(){
        new Thread(new RunnableImplement("A")).start();
        new Thread(new RunnableImplement("B")).start();
        new Thread(new RunnableImplement("C")).start();
    }

    /**
     * 多线程的第三种写法，方便获取返回值
     */
    public static void callbackImplement() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTaskA = new FutureTask<String>(new CallbackImplement("A",3));
        FutureTask<String> futureTaskB = new FutureTask<String>(new CallbackImplement("B",2));
        FutureTask<String> futureTaskC = new FutureTask<String>(new CallbackImplement("C",1));

        new Thread(futureTaskA).start();
        new Thread(futureTaskB).start();
        new Thread(futureTaskC).start();

        System.out.println(futureTaskA.get());
        System.out.println(futureTaskB.get());
        System.out.println(futureTaskC.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //threadExtend();
        //runnableImplement();
        callbackImplement();
    }

}

/**
 * 多线程的第一种写法，继承Thread
 *
 */
class ThreadExtend extends Thread{
    private String name = "";
    public ThreadExtend(String name){
        //这里可以改变线程的名字
        super(name);
        this.name = name;
    }
    @Override
    public void run() {
        int i = 0;
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + name + ": " + i++);
        }
    }
}

/**
 * 多线程的第二种写法，实现Runnable
 */
class RunnableImplement implements Runnable{
    private String name = "";
    public RunnableImplement(String name){
        this.name = name;
    }
    @Override
    public void run() {
        int i = 0;
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Implementation Thread " + name + ": " + i++);
        }
    }
}

/**
 * 多线程的第二种写法，可以获取返回值
 */
class CallbackImplement implements Callable<String> {
    private String name = "";
    private int second = 0;
    public CallbackImplement(String name, int second){
        this.name = name;
        this.second = second * 1000;
    }
    @Override
    public String call() throws Exception {
        Thread.sleep(second);
        return "result :" + name + "; second is " + second;
    }
}