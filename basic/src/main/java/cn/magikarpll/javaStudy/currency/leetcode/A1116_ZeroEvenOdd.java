package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class A1116_ZeroEvenOdd {
}


class ZeroEvenOdd {
    private int n;
    private Lock lock = new ReentrantLock();
    private Condition zeroCondition = lock.newCondition();
    private Condition oddCondition = lock.newCondition();
    private int zeroStatus = 1;
    private int oddStatus = 1;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            try {
                lock.lock();
                while (zeroStatus == 0){
                    oddCondition.signalAll();
                    zeroCondition.await();
                }
                printNumber.accept(0);
                zeroStatus = 0;
                oddCondition.signalAll();
                zeroCondition.signalAll();
            } finally {
                lock.unlock();
            }

        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {

        for (int i = 1; i <= n; i++) {
            if(i % 2 == 1){
                continue;
            }
            try {
                lock.lock();
                while (oddStatus == 1) {
                    oddCondition.await();
                }
                while (zeroStatus == 1){
                    zeroCondition.await();
                }
                printNumber.accept(i);
                oddStatus = 1;
                zeroStatus = 1;
                zeroCondition.signal();
            } finally {
                lock.unlock();
            }
        }

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {

        for (int i = 1; i <= n; i++) {
            if(i % 2 == 0){
                continue;
            }
            try {
                lock.lock();
                while (oddStatus == 0) {
                    oddCondition.await();
                }
                while (zeroStatus == 1){
                    zeroCondition.await();
                }
                printNumber.accept(i);
                oddStatus = 0;
                zeroStatus = 1;
                zeroCondition.signal();
            } finally {
                lock.unlock();
            }
        }

    }
}