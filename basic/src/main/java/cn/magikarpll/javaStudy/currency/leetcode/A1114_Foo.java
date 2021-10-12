package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.concurrent.Semaphore;

public class A1114_Foo {
}

class Foo {

    private static final Semaphore first = new Semaphore(0);
    private static final Semaphore second = new Semaphore(0);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        first.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        first.acquire();
        printSecond.run();
        second.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        second.acquire();
        printThird.run();
    }
}