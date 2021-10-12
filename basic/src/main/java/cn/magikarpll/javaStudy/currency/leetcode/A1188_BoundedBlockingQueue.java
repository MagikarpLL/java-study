package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class A1188_BoundedBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // 使用capacity = 2初始化队列。

        Thread proCon = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(500);
                    queue.enqueue(1);   // 生产者线程将1插入队列。

                    queue.enqueue(0);   // 生产者线程将0插入队列。消费者线程被解除阻塞同时将0弹出队列并返回。

                    queue.enqueue(2);   // 生产者线程将2插入队列。
                    queue.enqueue(3);   // 生产者线程将3插入队列。
                    queue.enqueue(4);   // 生产者线程由于队列长度已达到上限2而被阻塞。
                }catch (InterruptedException ie){

                }
            }
        });

        Thread conCon = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    queue.dequeue();    // 消费者线程调用dequeue并返回1。
                    queue.dequeue();    // 由于队列为空，消费者线程被阻塞。
                    queue.dequeue();    // 消费者线程将2从队列弹出并返回。生产者线程解除阻塞同时将4插入队列。
                }catch (InterruptedException ie){

                }
            }
        });

        proCon.start();
        conCon.start();
        queue.size();       // 队列中还有2个元素。size()方法在每组测试用例最后调用。
        Thread.sleep(5000);
    }


}


class BoundedBlockingQueue {

    private LinkedList<Integer> linkedList = new LinkedList<Integer>();
    private volatile int capacity = 0;
    private Lock lock = new ReentrantLock(true);
    private Condition proCon = lock.newCondition();
    private Condition conCon = lock.newCondition();

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void enqueue(int element) throws InterruptedException {
        try {
            lock.lock();
            while(linkedList.size() >= capacity){
                conCon.signalAll();
                proCon.await();
            }
            linkedList.addFirst(element);
            conCon.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        int result = 0;
        try {
            lock.lock();
            while(linkedList.size() <= 0){
                proCon.signalAll();
                conCon.await();
            }
            result = linkedList.removeLast();
            proCon.signalAll();
        }finally {
            lock.unlock();
        }
        return result;
    }

    public int size() {
        return linkedList.size();
    }
}