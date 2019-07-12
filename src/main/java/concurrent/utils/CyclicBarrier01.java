package concurrent.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class CyclicBarrier01 {

    static class MyRunnable implements Callable<Boolean> {

        private CyclicBarrier barrier;

        public MyRunnable(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public Boolean call() throws Exception {
            System.out.println("Thread:" + Thread.currentThread().getName());
            this.barrier.await();

            return null;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CyclicBarrier barrier = new CyclicBarrier(5);
        IntStream.range(1, 50).forEach(value -> {
            executorService.submit(new MyRunnable(barrier));
        });
    }
}
