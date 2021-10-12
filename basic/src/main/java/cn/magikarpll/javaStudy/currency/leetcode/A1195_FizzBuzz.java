package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class A1195_FizzBuzz {
}

class FizzBuzz {
    private int n;
    private Semaphore fizzSeamphore = new Semaphore(0);
    private Semaphore buzzSeamphore = new Semaphore(0);
    private Semaphore fizzBuzzSeamphore = new Semaphore(0);
    private Semaphore intSeamphore = new Semaphore(0);


    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".   3
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 != 0 || i % 15 == 0) {
                continue;
            }
            fizzSeamphore.acquire();
            printFizz.run();
            intSeamphore.release();
        }
    }

    // printBuzz.run() outputs "buzz".   5
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 5 != 0 || i % 15 == 0) {
                continue;
            }
            buzzSeamphore.acquire();
            printBuzz.run();
            intSeamphore.release();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".   15
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 15 != 0) {
                continue;
            }
            fizzBuzzSeamphore.acquire();
            printFizzBuzz.run();
            intSeamphore.release();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                fizzBuzzSeamphore.release();
                if (i != n) {
                    intSeamphore.acquire();
                }
                continue;
            } else if (i % 3 == 0) {
                fizzSeamphore.release();
                if (i != n) {
                    intSeamphore.acquire();
                }
                continue;
            } else if (i % 5 == 0) {
                buzzSeamphore.release();
                if (i != n) {
                    intSeamphore.acquire();
                }
                continue;
            }
            printNumber.accept(i);
        }
    }
}