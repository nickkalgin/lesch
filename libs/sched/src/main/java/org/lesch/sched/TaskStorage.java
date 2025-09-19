package org.lesch.sched;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskStorage {
    List<Task> findByDate(LocalDateTime before);
}
