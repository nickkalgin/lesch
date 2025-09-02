package org.lesch.tasks;

import java.time.LocalDateTime;

public class RecurringTask implements Task {

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int repetitions;

    public RecurringTask(LocalDateTime startAt, LocalDateTime endAt, int repetitions) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.repetitions = repetitions;
    }

    @Override
    public String id() {
        return "";
    }

    @Override
    public void remove() {

    }
}
