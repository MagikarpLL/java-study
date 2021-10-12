package cn.magikarpll.javaStudy.currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static cn.magikarpll.javaStudy.currency.A2_Synchronized.*;

public class A2_Synchronized {

    /**
     * 阻塞方式：put()、take()。
     * 非阻塞方式：offer()、poll()
     */
    public static final Queue<Object> storeQueue = new ArrayBlockingQueue<Object>(10);

    public static final List<Object> storeList = new ArrayList<>(10);
    public static final Object consumerLock = new Object();
    public static final Object producerLock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread consumerA = new Consumer("C1");
        Thread consumerB = new Consumer("C2");
        Thread producerA = new Producer("P1");
        Thread producerB = new Producer("P2");
        Thread producerC = new Producer("P3");
        consumerA.start();
        consumerB.start();
        producerA.start();
        producerB.start();
        producerC.start();
        consumerA.join();
    }

    public static Object initObject(){
        return new Object();
    }

}

/**
 * 使用wait and notify 完成经典的生产者消费者模式
 */
class Consumer extends Thread{

    private String name;

    public Consumer(String name){
        this.name = name;
    }

    public void consumeQueue(){
        int i = 0;
        for(;;){
            synchronized (producerLock){
                try{
                    Thread.sleep(500);
                    if(storeQueue.isEmpty()){
                        System.out.println(name + "队列为空，消费者等待!");
                        producerLock.wait();
                    }else {
                        storeQueue.poll();
                        i++;
                        System.out.println(name + "消费者消费完成，当前队列共有"+ storeQueue.size()  +"个,共消费" + i + "个，通知生产者!");
                        producerLock.notify();
                    }
                }catch (InterruptedException e){
                    System.out.println(name + "消费者被中断!");
                }
            }
            Thread.yield();
        }
    }

    public void consumeList(){
        for(;;){
            synchronized (storeList){
                try{
                    Thread.sleep(700);
                    if(storeList.size() <= 0){
                        System.out.println(name + "队列为空，消费者等待!");
                        producerLock.wait();
                    }else {
                        storeList.remove(0);
                        System.out.println(name + "消费者消费完成，当前队列共有"+ storeList.size()  +"个，通知生产者!");
                        producerLock.notify();
                        //Thread.yield();
                        //break;
                    }
                }catch (InterruptedException e){
                    System.out.println(name + "消费者被中断!");
                }
            }
            Thread.yield();
        }
    }

    @Override
    public void run() {
        System.out.println("消费者开始消费!");
        //consumeList();
        consumeQueue();
        System.out.println("消费者结束消费!");
    }
}

class Producer extends Thread{

    private String name;

    public Producer(String name){
        this.name = name;
    }

    public void produceQueue(){
        int i = 0;
        for(;;){
            synchronized (consumerLock){
                try{
                    Thread.sleep(1000);
                    if(storeQueue.size() >= 10){
                        System.out.println(name + "队列已满，生产者开始等待!");
                        consumerLock.wait();
                    }else{
                        storeQueue.add(initObject());
                        i++;
                        System.out.println(name + "生产者生产完成，当前队列共有"+ storeQueue.size()  +"个,共生产" + i + "个，通知消费者!");
                        consumerLock.notify();
                    }
                }catch (InterruptedException e){
                    System.out.println(name + "生产者被中断!");
                }
            }
            Thread.yield();
        }
    }

    public void produceList(){
        for(;;){
            synchronized (storeList){
                try{
                    Thread.sleep(1000);
                    if(storeList.size() >= 10){
                        System.out.println(name + "队列已满，生产者开始等待!");
                        consumerLock.wait();
                    }else{
                        storeList.add(initObject());
                        System.out.println(name + "生产者生产完成，当前队列共有"+ storeList.size()  +"个，通知消费者!");
                        consumerLock.notify();
                        //Thread.yield();
                        //break;
                    }
                }catch (InterruptedException e){
                    System.out.println(name + "生产者被中断!");
                }
            }
            Thread.yield();
        }
    }

    @Override
    public void run() {
        System.out.println("生产者开始生产!");
        //produceList();
        produceQueue();
        System.out.println("生产者完成生产!");
    }
}


class TestJoin{

    public static void main(String[] args) throws InterruptedException {
        System.out.println("1:创建三个线程，分别睡眠1s 2s 3s");
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("6:A线程开始睡眠1s");
                    Thread.sleep(1000);
                    System.out.println("9:A线程睡眠结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("7:B线程开始睡眠2s");
                    Thread.sleep(2000);
                    System.out.println("12:B线程睡眠结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("8:C线程开始睡眠3s");
                    Thread.sleep(3000);
                    System.out.println("15:C线程睡眠结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("2:线程A开始执行");
        threadA.start();
        System.out.println("3:线程B开始执行");
        threadB.start();
        System.out.println("4:线程C开始执行");
        threadC.start();
        System.out.println("5:等待A线程执行完成");
        threadA.join();
        System.out.println("10:A线程执行完成");
        System.out.println("11:等待B线程执行完成");
        threadB.join();
        System.out.println("13:B线程执行完成");
        System.out.println("14:等待C线程执行完成");
        threadC.join();
        System.out.println("16:C线程执行完成");
    }


}