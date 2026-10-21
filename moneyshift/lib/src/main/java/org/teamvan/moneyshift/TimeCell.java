package org.teamvan.moneyshift;

import java.util.Objects;

/** One value on one timeline. */
public record TimeCell(Timeline timeline, long value) {
    public TimeCell {
        Objects.requireNonNull(timeline, "timeline");
    }
}
