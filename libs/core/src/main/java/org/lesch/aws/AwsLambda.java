package org.lesch.aws;

import org.lesch.Lambda;

import java.util.Objects;

public class AwsLambda implements Lambda {

    private final String arn;

    public AwsLambda(String id) {
        Objects.requireNonNull(id, "Lambda ID cannot be null");
        this.arn = id;
    }

    @Override
    public String id() {
        return arn;
    }

    @Override
    public String toString() {
        return "AwsLambda{arn='%s'}".formatted(arn);
    }
}
