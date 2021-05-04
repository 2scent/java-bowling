package bowling.domain;

import java.util.Objects;

public final class Pitch {
    private static final int MIN_KNOCKED_PINS_COUNT = 0;
    private static final int MAX_KNOCKED_PINS_COUNT = 10;
    private static final String ILLEGAL_KNOCKED_PINS_COUNT_MESSAGE =
            String.format("knockedPinsCount는 %d 이상 %d 이하여야 합니다.", MIN_KNOCKED_PINS_COUNT, MAX_KNOCKED_PINS_COUNT);

    private final int knockedPinsCount;

    public Pitch(final int knockedPinsCount) {
        if (knockedPinsCount < MIN_KNOCKED_PINS_COUNT || knockedPinsCount > MAX_KNOCKED_PINS_COUNT) {
            throw new IllegalArgumentException(ILLEGAL_KNOCKED_PINS_COUNT_MESSAGE);
        }

        this.knockedPinsCount = knockedPinsCount;
    }

    public boolean gutter() {
        return knockedPinsCount == MIN_KNOCKED_PINS_COUNT;
    }

    public int knockedPinsCount() {
        return knockedPinsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pitch pitch = (Pitch) o;
        return knockedPinsCount == pitch.knockedPinsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(knockedPinsCount);
    }
}