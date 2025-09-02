package org.lesch.aws;

import org.lesch.Job;
import org.lesch.Lambda;
import software.amazon.awssdk.services.scheduler.model.ScheduleState;

import java.time.LocalDateTime;
import java.util.Objects;

public class AwsJob implements Job {

    private final String id;
    private final ScheduleState state;
    private final Lambda lambda;
    private Repeat mode;
    private int repeatRate;

    public AwsJob(String id, Lambda lambda) {
        this(id, ScheduleState.ENABLED, lambda);
        mode = Repeat.ONCE;
    }

    public AwsJob(String id, Repeat repeat, int value, Lambda lambda) {
        this(id, ScheduleState.ENABLED, lambda);
        mode = repeat;
        repeatRate = value;
    }

    public AwsJob(String id, ScheduleState state, Lambda lambda) {
        Objects.requireNonNull(id, "Job ID cannot be null");
        Objects.requireNonNull(state, "Job state cannot be null");
        Objects.requireNonNull(lambda, "Job code cannot be null");

        this.id = id;
        this.state = state;
        this.lambda = lambda;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public State state() {
        return switch (state) {
            case ENABLED -> State.ENABLED;
            case DISABLED -> State.DISABLED;
            default -> State.UNKNOWN;
        };
    }

    @Override
    public Lambda lambda() {
        return lambda;
    }

    @Override
    public String scheduleExpression() {
        return switch (mode) {
            case ONCE -> "at(%s)".formatted(LocalDateTime.now());
            case MINUTES -> "rate(%s minute)".formatted(repeatRate);
            case HOURS -> "rate(%s hour)".formatted(repeatRate);
            case DAYS -> "rate(%s day)".formatted(repeatRate);
        };
    }

    @Override
    public String toString() {
        return "AwsJob{id='%s', state=%s, lambda=%s}".formatted(id, state, lambda);
    }
}
