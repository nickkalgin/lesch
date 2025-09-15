package org.lesch.aws;

import software.amazon.awssdk.services.scheduler.model.ScheduleState;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Scheduled task representation.
 */
public class AwsJob {
    public enum State {
        ENABLED, DISABLED, UNKNOWN
    }

    public enum Repeat {
        ONCE, MINUTES, HOURS, DAYS
    }

    private final String id;
    private final ScheduleState state;
    private final Target target;
    private Repeat repeatKind;

    private int repeatRate;

    public AwsJob(String id, Repeat repeatKind, int repeatRate, Target target) {
        this(id, ScheduleState.ENABLED, target);
        this.repeatKind = repeatKind;
        this.repeatRate = repeatRate;
    }

    public AwsJob(String id, ScheduleState state, Target target) {
        Objects.requireNonNull(id, "Job ID cannot be null");
        Objects.requireNonNull(state, "Job state cannot be null");
        Objects.requireNonNull(target, "Job code cannot be null");

        this.id = id;
        this.state = state;
        this.target = target;
    }

    public String id() {
        return id;
    }

    public State state() {
        return switch (state) {
            case ENABLED -> State.ENABLED;
            case DISABLED -> State.DISABLED;
            default -> State.UNKNOWN;
        };
    }

    public Target target() {
        return target;
    }

    public String scheduleExpression() {
        return switch (repeatKind) {
            case ONCE -> "at(%s)".formatted(LocalDateTime.now());
            case MINUTES -> "rate(%s minute)".formatted(repeatRate);
            case HOURS -> "rate(%s hour)".formatted(repeatRate);
            case DAYS -> "rate(%s day)".formatted(repeatRate);
        };
    }

    @Override
    public String toString() {
        return "AwsJob{id='%s', state=%s, lambda=%s}".formatted(id, state, target);
    }
}
