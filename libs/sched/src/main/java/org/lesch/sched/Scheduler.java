package org.lesch.sched;

import java.util.concurrent.TimeUnit;

public interface Scheduler {

    void start();

    void stop(long timeoutMillis);
}
