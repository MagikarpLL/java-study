package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class A1115_FooBar {
}


/**
 * leetcode  1115题
 */
class FooBar_CAS {
    private int n;
    private static final AtomicBoolean status = new AtomicBoolean(true);

    public FooBar_CAS(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            for (; ; ) {
                if (status.compareAndSet(true, false)) {
                    printFoo.run();
                    break;
                } else {
                    Thread.yield();
                }
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            for (; ; ) {
                if (status.compareAndSet(false, true)) {
                    printBar.run();
                    break;
                } else {
                    Thread.yield();
                }
            }
        }
    }
}

/**
 *
 */
class FooBar_LOCK {
    private int n;
    private static final Lock lock = new ReentrantLock();
    private static Condition fooCondition = lock.newCondition();
    private static Condition barCondition = lock.newCondition();
    private static volatile int status = 0;

    public FooBar_LOCK(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                lock.lock();
                printFoo.run();
                status = 1;
                System.out.println(Thread.currentThread().getName() + "foo wait");
                barCondition.signal();
                if(status != 0){
                    barCondition.signal();
                    fooCondition.await();
                }

                if( i == n-1){
                    barCondition.signal();
                }
            } catch (InterruptedException ie) {

            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "bar wait");
                if(status != 1){
                    fooCondition.signal();
                    barCondition.await();
                }
                printBar.run();
                status = 0;
                if(i == n-1){
                    fooCondition.signal();
                }
            } catch (InterruptedException ie) {

            } finally {
                lock.unlock();
            }
        }
    }
}

/**
 *
 */
class FooBar_Semaphore {
    private int n;
    private static final Semaphore semaphore = new Semaphore(1);

    public FooBar_Semaphore(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        try {
            for (int i = 0; i < n; i++) {
                if (semaphore.availablePermits() == 1) {
                    semaphore.acquire(1);
                    printFoo.run();
                    semaphore.release(1);
                } else {

                }
                // printFoo.run() outputs "foo". Do not change or remove this line.

            }
        } catch (InterruptedException ie) {

        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        try {
            for (int i = 0; i < n; i++) {
                if (semaphore.availablePermits() == 0) {
                    printBar.run();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
            }
        } catch (Exception ie) {

        }
    }
}


class FooBar_Cyclic {
    private int n;
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    private static volatile int status = 1;

    public FooBar_Cyclic(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " foo");
        try {
            for (int i = 0; i < n; i++) {
                if (status == 1) {
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    status = 0;
                    cyclicBarrier.await();
                } else {
                    cyclicBarrier.await();
                    printFoo.run();
                    status = 0;
                }
            }
        } catch (InterruptedException | BrokenBarrierException ie) {

        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " bar");
        try {
            for (int i = 0; i < n; i++) {
                if (status == 0) {
                    // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    status = 1;
                    cyclicBarrier.await();
                } else {
                    cyclicBarrier.await();
                    // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    status = 1;
                }
            }
        } catch (InterruptedException | BrokenBarrierException ie) {

        }
    }
}


/**
 * 几种方式:
 */

//手太阴肺经 BLOCKING Queue
class FooBar1 {
    private int n;
    private BlockingQueue<Integer> bar = new LinkedBlockingQueue<>(1);
    private BlockingQueue<Integer> foo = new LinkedBlockingQueue<>(1);
    public FooBar1(int n) {
        this.n = n;
    }
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.put(i);
            printFoo.run();
            bar.put(i);
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.take();
            printBar.run();
            foo.take();
        }
    }
}

//手阳明大肠经CyclicBarrier 控制先后
class FooBar6 {
    private int n;

    public FooBar6(int n) {
        this.n = n;
    }

    CyclicBarrier cb = new CyclicBarrier(2);
    volatile boolean fin = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(!fin);
            printFoo.run();
            fin = false;
            try {
                cb.await();
            } catch (BrokenBarrierException e) {}
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                cb.await();
            } catch (BrokenBarrierException e) {}
            printBar.run();
            fin = true;
        }
    }
}

//手少阴心经 自旋 + 让出CPU
class FooBar5 {
    private int n;

    public FooBar5(int n) {
        this.n = n;
    }

    volatile boolean permitFoo = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; ) {
            if(permitFoo) {
                printFoo.run();
                i++;
                permitFoo = false;
            }else{
                Thread.yield();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; ) {
            if(!permitFoo) {
                printBar.run();
                i++;
                permitFoo = true;
            }else{
                Thread.yield();
            }
        }
    }
}



//手少阳三焦经 可重入锁 + Condition
class FooBar4 {
    private int n;

    public FooBar4(int n) {
        this.n = n;
    }
    Lock lock = new ReentrantLock(true);
    private final Condition foo = lock.newCondition();
    volatile boolean flag = true;
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while(!flag) {
                    foo.await();
                }
                printFoo.run();
                flag = false;
                foo.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n;i++) {
            lock.lock();
            try {
                while(flag) {
                    foo.await();
                }
                printBar.run();
                flag = true;
                foo.signal();
            }finally {
                lock.unlock();
            }
        }
    }
}

//手厥阴心包经 synchronized + 标志位 + 唤醒
class FooBar3 {
    private int n;
    // 标志位，控制执行顺序，true执行printFoo，false执行printBar
    private volatile boolean type = true;
    private final Object foo=  new Object(); // 锁标志

    public FooBar3(int n) {
        this.n = n;
    }
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (foo) {
                while(!type){
                    foo.wait();
                }
                printFoo.run();
                type = false;
                foo.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (foo) {
                while(type){
                    foo.wait();
                }
                printBar.run();
                type = true;
                foo.notifyAll();
            }
        }
    }
}


//手太阳小肠经 信号量 适合控制顺序
class FooBar2 {
    private int n;
    private Semaphore foo = new Semaphore(1);
    private Semaphore bar = new Semaphore(0);
    public FooBar2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }
    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }
}
