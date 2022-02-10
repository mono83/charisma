package com.github.mono83.charisma.persistence;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoLoadingQueue extends LoadingQueue<String> {
    @Override
    protected String performLoad(long id) {
        return "";
    }

    public static void runSingleMode(LoadingQueue<?> queue, int parallel, int count, int cardinalityCandidate) throws InterruptedException {
        Thread[] threads = new Thread[parallel];
        AtomicInteger counter = new AtomicInteger();
        int cardinality = (cardinalityCandidate == 0 || cardinalityCandidate > count)
                ? count
                : cardinalityCandidate;

        for (int i = 0; i < parallel; i++) {
            final boolean backwards = i % 2 == 0;
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

        long before = System.currentTimeMillis();

        for (int i = 0; i < parallel; i++) {
            threads[i].start();
        }

        for (int i = 0; i < parallel; i++) {
            threads[i].join();
        }

        long delta = System.currentTimeMillis() - before;

        System.out.printf(
                "Settings:%dT*%d%%%d   Served: %d   Queued: %d   MaxQ: %d   NowQ: %d   Elapsed: %.3fs   RPS: %d%n",
                parallel,
                count,
                cardinality,
                counter.get(),
                queue.queued,
                queue.queueMax,
                queue.queueSize,
                (float) delta / 1000.f,
                count * parallel * 1000L / delta
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DemoLoadingQueue queue = new DemoLoadingQueue();

        while (true) {
            runSingleMode(queue, 200, 10000, 100);
        }
    }
}
