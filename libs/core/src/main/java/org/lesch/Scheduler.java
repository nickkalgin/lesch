package org.lesch;

import java.util.List;

public interface Scheduler {

    String schedule(String name, Job job);

    List<Job> list();
}
