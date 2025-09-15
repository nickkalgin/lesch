package org.lesch.aws;

import java.util.Objects;

public record Target(String id) {

    public Target {
        Objects.requireNonNull(id, "Target ID cannot be null");
    }

    @Override
    public String toString() {
        return "Target{id='%s'}".formatted(id);
    }
}
