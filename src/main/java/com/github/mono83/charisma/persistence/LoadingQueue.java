package com.github.mono83.charisma.persistence;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Abstraction to load data in single thread.
 *
 * @param <T> Data type.
 */
public abstract class LoadingQueue<T> {
    /**
     * Primary lock.
     */
    private final Object lock = new Object();
    /**
     * Secondary lock to use only when new action placed into queue.
     */
    private final Object notifier = new Object();
    /**
     * Queue root
     */
    private volatile Node<T> root;
    /**
     * Cumulative counter of queued items.
     */
    volatile long queued = 0;
    /**
     * Current queue size.
     */
    volatile long queueSize = 0;
    /**
     * Max queue size.
     */
    volatile long queueMax = 0;

    /**
     * Main constructor.
     *
     * @param factory Thread factory to use for data loading, optional.
     */
    protected LoadingQueue(final ThreadFactory factory) {
        Thread thread;
        if (factory == null) {
            thread = new Thread(this::runnable);
            thread.setName("Loader for " + this.getClass().getName());
            thread.setDaemon(true);
        } else {
            thread = factory.newThread(this::runnable);
        }
        thread.start();
    }

    /**
     * Constructor.
     * Will create new thread for data loading.
     */
    protected LoadingQueue() {
        this(null);
    }

    /**
     * Internal method that will run in dedicated thread and load data.
     */
    private void runnable() {
        while (true) {
            long id = 0;
            Promise<T> promise = null;
            synchronized (lock) {
                if (root != null) {
                    id = root.id;
                    promise = root.future;
                }
            }

            if (promise != null) {
                // Loading
                T data = performLoad(id);

                // Saving
                synchronized (promise.notifier) {
                    promise.data = data;
                    promise.done = true;

                    // Notifying
                    promise.notifier.notifyAll();
                }

                // Releasing
                synchronized (lock) {
                    root = root.next;
                    queueSize--;
                }
            } else {
                try {
                    synchronized (notifier) {
                        notifier.wait();
                    }
                } catch (InterruptedException e) {
                    // Graceful exit
                    return;
                }
            }
        }
    }

    /**
     * Returns future of data.
     *
     * @param id Identifier of data.
     * @return Data.
     */
    public Future<T> load(long id) {
        synchronized (lock) {
            Future<T> future = root == null ? null : root.find(id);
            if (future != null) {
                // Found existing
                return future;
            }

            // Creating new future
            Promise<T> promise = new Promise<>();

            // Creating new node
            Node<T> node = new Node<>(id, promise);

            // Storing node
            if (root == null) {
                root = node;
            } else {
                root.place(node);
            }

            // Scheduling
            synchronized (notifier) {
                notifier.notify();
            }

            queued++;
            queueSize++;
            if (queueSize > queueMax) {
                queueMax = queueSize;
            }

            return promise;
        }
    }

    /**
     * Performs actual load of entity.
     *
     * @param id Identifier of entity.
     * @return Entity or null.
     */
    protected abstract T performLoad(long id);

    private static final class Node<T> {
        private final long id;
        private final Promise<T> future;
        private volatile Node<T> next;

        Node(long id, Promise<T> future) {
            this.id = id;
            this.future = future;
        }

        Future<T> find(long id) {
            Node<T> current = this;
            while (current != null) {
                if (current.id == id) {
                    return current.future;
                }
                current = current.next;
            }
            return null;
        }

        void place(Node<T> node) {
            Node<T> current = this;
            while (current != null) {
                if (current.next == null) {
                    current.next = node;
                    return;
                }
                current = current.next;
            }
        }
    }

    private static final class Promise<T> implements Future<T> {
        private final Object notifier = new Object();
        private volatile boolean done;
        private T data;

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false; // TODO Not implemented
        }

        @Override
        public boolean isCancelled() {
            return false; // TODO Not implemented
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public T get() throws InterruptedException {
            if (!this.done) {
                synchronized (notifier) {
                    if (!this.done) {
                        notifier.wait();
                    }
                }
            }
            return this.data;
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException {
            if (!this.done) {
                synchronized (notifier) {
                    if (!this.done) {
                        notifier.wait(unit.toMillis(timeout));
                    }
                }
            }
            return this.data;
        }
    }
}
