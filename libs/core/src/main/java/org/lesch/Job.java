package org.lesch;

public interface Job {

    enum State {
        ENABLED, DISABLED, UNKNOWN
    }

    enum Repeat {
        ONCE, MINUTES, HOURS, DAYS
    }

    String id();

    State state();

    Lambda lambda();

    String scheduleExpression();
}
