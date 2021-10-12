package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class A1117_H2O {

    public A1117_H2O() {

    }

}


/**
 * 锁解法
 */
class H2O_LOCK {

    private static final Lock lock = new ReentrantLock();
    private static final Condition hCondition = lock.newCondition();
    private static final Condition oCondition = lock.newCondition();
    private volatile static int num = 0;

    public H2O_LOCK() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        try {
            lock.lock();
            while (num == 2){
                oCondition.signalAll();
                hCondition.await();
            }
            releaseHydrogen.run();
            num++;
            hCondition.signalAll();
            oCondition.signalAll();
        }catch (InterruptedException ie){

        }finally {
            lock.unlock();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        try {
            lock.lock();
            while (num != 2){
                hCondition.signalAll();
                oCondition.await();
            }
            releaseOxygen.run();
            num = 0;
            hCondition.signalAll();
        }catch (InterruptedException ie){

        }finally {
            lock.unlock();
        }
    }
}


/**
 *  CAS解法
 */
class H2O_CAS {

    private static AtomicInteger num = new AtomicInteger(0);

    public H2O_CAS() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "-H");
        for(;;){
            if(num.get() == 0){
                if(num.compareAndSet(0, 1)){
                    // releaseOxygen.run() outputs "O". Do not change or remove this line.
                    releaseHydrogen.run();
                    break;
                }
            }
            if (num.get() == 1){
                if(num.compareAndSet(1, 2)){
                    // releaseOxygen.run() outputs "O". Do not change or remove this line.
                    releaseHydrogen.run();
                    break;
                }
            }
            Thread.yield();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "-O");
        for(;;){
            if(num.get() == 2){
                if(num.compareAndSet(2, 0)){
                    // releaseOxygen.run() outputs "O". Do not change or remove this line.
                    releaseOxygen.run();
                    break;
                }
            }
            Thread.yield();
        }
    }
}

/**
 * 锁解法TEST
 * 所以综上，假如上一个获取lock的线程unlock，那么在lock上等待的下一个线程，是可以自动获取到锁的；但是在lock的condition上等待的线程
 * 是一定要被signal唤醒的，不然就停在那里。 也就是说,unlock并不能唤醒在condition上等待await的线程，await必须是与signal配套使用
 */
class H2O_TEST_LOCK {

    private static final Lock lock = new ReentrantLock();
    private static final Condition hCondition = lock.newCondition();
    private static final Condition oCondition = lock.newCondition();
    private volatile static int num = 0;

    public H2O_TEST_LOCK() {

    }

    public static void main(String[] args) {
        Random random = new Random();
        List<Thread> threadList = new ArrayList<Thread>();
        H2O_TEST_LOCK h2O_test_lock = new H2O_TEST_LOCK();
        for(int num = 0; num < 9; num ++){
            if(num <=5){
                Thread threadH = h2O_test_lock.new ThreadH();
                threadList.add(threadH);
            }else{
                Thread threadO = h2O_test_lock.new ThreadO();
                threadList.add(threadO);
            }
        }
        Collections.shuffle(threadList);

        try {
            for(Thread t : threadList){
                t.start();
            }

            Thread.sleep(10000);
        }catch (InterruptedException ie){

        }
    }

    private class ThreadH extends Thread{
        @Override
        public void run() {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "H get lock");
                    while (num == 2){
                        //o数量较少，所以当h挂起之前，一定记得唤醒o不然就没人signal了
                        oCondition.signalAll();
                        System.out.println(Thread.currentThread().getName() + "H WAIT");
                        hCondition.await();
                    }
                    System.out.println("H");
                    num++;
                    //当ohh按这个顺序获取到锁时，由于o已经等待了，因此这里假如满了，得通知o，不然就超时了
                    if(num == 2){
                        oCondition.signalAll();
                    }
                }catch (InterruptedException ie){

                }finally {
                    System.out.println(Thread.currentThread().getName() + "H EXIT");
                    lock.unlock();
                }
        }
    }

    private class ThreadO extends Thread{
        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "O get lock");
                while (num != 2){
                    //这里不用唤醒h是因为，当num不为2时，o在这里等待，然后一定会有一个h没执行，当它执行后会唤醒o
                    System.out.println(Thread.currentThread().getName() + "O WAIT");
                    oCondition.await();
                }
                System.out.println("O");
                num = 0;
                //当o执行完成后，通知h生产，防止 hhhhoo获取锁的这种情况，hh生产好了，hh等待，o消费完第一个如果不通知h，就锁住了
                hCondition.signalAll();
            }catch (InterruptedException ie){

            }finally {
                System.out.println(Thread.currentThread().getName() + "O EXIT");
                lock.unlock();
            }
        }
    }

}