package cn.magikarpll.javaStudy.currency.leetcode;

import java.util.concurrent.Semaphore;

public class A1226_DiningPhilosophers {
}


class DiningPhilosophers {

    public DiningPhilosophers() {

    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

    }
}


/**
 * 不行，必须要等待
 * 当有5个人的时候，乐观解法不行，必须阻塞。
 * 会反复失败多次
 */
class DiningPhilosophers_For {

    private Semaphore[] semaphores = new Semaphore[5];
    private Semaphore limit = new Semaphore(3);

    public DiningPhilosophers_For() {
        for (int i = 0; i < 5; i++) {
            semaphores[i] = new Semaphore(1);
        }
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        limit.acquire(1);
        semaphores[philosopher].acquire(1);
        pickLeftFork.run();
        semaphores[(philosopher + 1) % 5].tryAcquire(1);
        pickRightFork.run();
        eat.run();
        semaphores[philosopher].release(1);
        putLeftFork.run();
        semaphores[(philosopher + 1) % 5].release(1);
        putRightFork.run();
        limit.release(1);
    }
}
