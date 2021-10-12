package cn.magikarpll.javaStudy.currency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

public class A4_ThreadPool {
}

class ForkJoinTaskExample extends RecursiveTask<Integer> {

    public static final int threshold = 2;
    private int start;
    private int end;

    public ForkJoinTaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            System.out.println("开始执行，当前线程为: " + Thread.currentThread().getName());
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start, middle);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1, end);
            //执行子任务
            leftTask.fork();
            System.out.println("开始分裂,left start is  " + start + "; left end is " + middle);
            rightTask.fork();
            System.out.println("开始分裂,right start is  " + middle + "; right end is " + end);
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> future = forkJoinPool.submit(new ForkJoinTaskExample(1,100));
        int result = future.get();
        System.out.println("complete: result is " + result);
    }
}