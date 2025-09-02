package org.lesch;

import org.junit.jupiter.api.Test;
import org.lesch.tasks.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

class SchedulerTest {

    @Test
    void test() {
        Task task = Scheduler.startAt(LocalDate.of(2025, 11, 3)).endAt(LocalDate.of(2025, 11, 3)).build();
        Scheduler.startAt(LocalDate.of(2025, 11, 3)).repeat().hours(2).endAt(LocalDate.of(2025, 11, 3));
        Scheduler.startAt(LocalDateTime.of(2025, 11, 3, 11, 12, 13)).repeat().minutes(2);
        Scheduler.startAt(LocalDateTime.of(2025, 11, 3, 11, 12, 13)).repeat().days(1);
        Scheduler.startAt(LocalDateTime.of(2025, 11, 3, 11, 12, 13)).repeat().hours(3);

        Scheduler.findTask(task.id()).remove();
    }
}