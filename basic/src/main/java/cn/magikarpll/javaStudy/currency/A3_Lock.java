package cn.magikarpll.javaStudy.currency;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class A3_Lock {
}

/**
 * 读写锁一般有两种使用场景，一种是比较简单的封装一个list
 */
class Test1_ReentrantReadWriteLock{
    private ReentrantReadWriteLock  reentrantReadWriteLock = new ReentrantReadWriteLock();
    private LinkedList<Object> linkedList = new LinkedList<Object>();
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    public void add(Object o){
        try{
            writeLock.lock();
            linkedList.add(o);
        }finally {
            writeLock.unlock();
        }
    }
    public Object remove(){
        try{
            writeLock.lock();
            return linkedList.removeLast();
        }finally {
            writeLock.unlock();
        }
    }
    public Object get(int i){
        try {
            readLock.lock();
            return linkedList.get(i);
        }finally {
            readLock.unlock();
        }
    }
}

/**
 * 比较复杂的用法，常用于判断缓存有效性中
 */
class Test2_ReentrantReadWriteLock{
    private String data = "初始数据";
    private volatile boolean cacheValid;
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        try{
            rwl.readLock().lock();
            if(!cacheValid){
                //如果数据无效，则要重新获取数据
                //因为要获取数据，因此要获取写锁，在这之前必须要释放读锁
                rwl.readLock().unlock();
                try{
                    rwl.writeLock().lock();
                    //这里要双重判断一下，防止在获取读锁期间，有其他线程修改了有效性
                    if(!cacheValid){
                        data = "更新数据";
                        cacheValid = true;
                    }
                    //锁降级
                    rwl.readLock().lock();
                }finally {
                    rwl.writeLock().unlock();
                }
            }
            //假如数据有效，则直接消费数据
            System.out.println(data + "被消费了");
            return;
        }finally {
            rwl.readLock().unlock();
        }
    }
}

/**
 * 关于CountDownLatch的一个很经典的例子
 */
class Test3_CountDownLatch{
    private static final CountDownLatch startSignal = new CountDownLatch(1);
    private static final CountDownLatch completeSignal = new CountDownLatch(5);
    public static void main(String[] args) throws InterruptedException {
        Test3_CountDownLatch test3_countDownLatch = new Test3_CountDownLatch();
        for(int i = 0; i < 5; i++){
            (test3_countDownLatch.new Worker(startSignal, completeSignal)).start();
        }
        System.out.println("worker等待开始!");
        Thread.sleep(1000);
        startSignal.countDown();
        Thread.sleep(1000);
        System.out.println("main等待结束!");
        completeSignal.await();
        System.out.println("已结束");
    }
    class Worker extends Thread{
        private final CountDownLatch startSignalInner;
        private final CountDownLatch completeSignalInner;
        public Worker(final CountDownLatch c1, final CountDownLatch c2){
            startSignalInner = c1;
            completeSignalInner = c2;
        }
        @Override
        public void run() {
            try{
                startSignalInner.await();
                System.out.println(Thread.currentThread().getName() + ": 我已开始!");
                Thread.sleep(2000);
                completeSignalInner.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}