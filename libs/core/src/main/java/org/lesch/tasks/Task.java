package org.lesch.tasks;

public interface Task {
    String id();

    void remove();

    TaskBuilder<Task, TaskBuilder<T, SELF>> builder();

    interface TaskBuilder<T, SELF extends TaskBuilder<T, SELF>> {
        T build();
    }
}
