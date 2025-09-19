package org.lesch.sched;

public interface Task extends Runnable {

    enum TaskState {
        PENDING,
        SCHEDULED,
        RUNNING,
        SUCCESS,
        FAILED,
        CANCELLED,
        COMPLETED;
    }

    Plan plan();
}
