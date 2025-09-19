package org.lesch.sched;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

public class ScheduledExecutor implements Scheduler {

    private final ScheduledExecutorService executorService;
    private final ExecutorService workers = Executors.newVirtualThreadPerTaskExecutor();
    private final int rateMillis;
    private final TaskStorage storage;

    public ScheduledExecutor(int rateMillis, TaskStorage storage) {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.storage = storage;
        this.rateMillis = rateMillis;
    }

    @Override
    public void start() {
        executorService.scheduleAtFixedRate(this::mainLoop, 0, rateMillis, TimeUnit.MILLISECONDS);
    }

    private void mainLoop() {
        var tasks = storage.findByDate(LocalDateTime.now());
        for (var task : tasks) {
            workers.submit(task);
        }
    }

    @Override
    public void stop(long timeoutMillis) {
        shutdown(workers, timeoutMillis);
        shutdown(executorService, timeoutMillis);
    }

    private void shutdown(ExecutorService executor, long timeoutMillis) {
        executor.shutdown();
        try {
            workers.awaitTermination(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // TODO add correct handling
            Thread.interrupted();
        }
    }
}
