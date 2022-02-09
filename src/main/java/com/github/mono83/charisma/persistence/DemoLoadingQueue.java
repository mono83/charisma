package com.github.mono83.charisma.persistence;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoLoadingQueue extends LoadingQueue<String> {
    @Override
    protected String performLoad(long id) {
        return "Hello " + id;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DemoLoadingQueue queue = new DemoLoadingQueue();

        long before = System.currentTimeMillis();
        int parallel = 250;
        int count = 10000;
        int cardinality = count;
        Thread[] threads = new Thread[parallel];
        AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < parallel; i++) {
            final boolean backwards =  i % 2 == 0;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    try {
                        queue.load((backwards ? count - j - 1 : j) % cardinality).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    counter.incrementAndGet();
                }
            });
        }
        for (int i = 0; i < parallel; i++) {
            threads[i].start();
        }

        for (int i = 0; i < parallel; i++) {
            threads[i].join();
        }

//        Thread.sleep(1000);

        long delta = System.currentTimeMillis() - before;

        System.out.println("Total   " + counter.get());
        System.out.println("Max     " + queue.queueMax);
        System.out.println("Queue   " + queue.queueSize);
        System.out.println("Queued  " + queue.queued);
        System.out.printf(
                "Elapsed %.3fs. RPS is %d%n",
                (float) delta / 1000.f,
                count * parallel * 1000L / delta
        );
    }
}
