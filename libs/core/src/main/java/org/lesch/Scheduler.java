package org.lesch;

import org.lesch.tasks.Task;
import org.lesch.tasks.TaskBuilderImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Scheduler {

    public static TaskBuilderImpl startAt(LocalDateTime executionTime) {
        return new TaskBuilderImpl().startAt(executionTime);
    }

    public static TaskBuilderImpl startAt(LocalDate executionDate) {
        return new TaskBuilderImpl().startAt(executionDate);
    }

    public static Task findTask(String taskId) {
        return null;
    }

    void removeTask(Task task) {}

    void listTasks() {}

    void viewTaskState(Task task) {}

    void addLambda() {}

    void removeLambda() {}

    void executeLambda() {}
}
