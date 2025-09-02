package org.lesch.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskBuilderImpl {

    protected Repetitions repetitions;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public TaskBuilderImpl startAt(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public TaskBuilderImpl startAt(LocalDate startDate) {
        this.startTime = LocalDateTime.from(startDate);
        return this;
    }

    public TaskBuilderImpl endAt(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public TaskBuilderImpl endAt(LocalDate endDate) {
        this.endTime = LocalDateTime.from(endDate);
        return this;
    }

    public Repetitions repeat() {
        repetitions = new Repetitions(this);
        return repetitions;
    }

    public Task build() {
        return new RecurringTask(startTime, endTime, repetitions.count);
    }

    public static class Repetitions {

        enum Unit { MINUTES, HOURS, DAYS }

        private final TaskBuilderImpl builder;
        private Unit unit;
        private int count;

        public Repetitions(TaskBuilderImpl builder) {
            this.builder = builder;
        }

        public TaskBuilderImpl minutes(int value) {
            count = value;
            unit = Unit.MINUTES;
            return builder;
        }

        public TaskBuilderImpl days(int value) {
            count = value;
            unit = Unit.DAYS;
            return builder;
        }

        public TaskBuilderImpl hours(int value) {
            count = value;
            unit = Unit.HOURS;
            return builder;
        }
    }
}
