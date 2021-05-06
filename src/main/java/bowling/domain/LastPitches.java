package bowling.domain;

import java.util.*;
import java.util.function.Consumer;

public final class LastPitches extends Pitches {
    private static final int MAX_PITCHES_COUNT = 3;
    private static final String OVER_MAX_PITCHES_COUNT_MESSAGE =
            String.format("pitches.size()는 %d 이하여야 합니다.", MAX_PITCHES_COUNT);
    private static final int STRIKE_PINS_COUNT = 10;
    private static final String WRONG_KNOCKED_PINS_COUNT_MESSAGE = "쓰러뜨린 핀의 개수가 올바르지 않습니다.";

    private LastPitches(final List<Pitch> pitches) {
        if (pitches.size() > MAX_PITCHES_COUNT) {
            throw new IllegalArgumentException(OVER_MAX_PITCHES_COUNT_MESSAGE);
        }

        if (pitches.size() == MAX_PITCHES_COUNT - 1) {
            validateTwoPitches(pitches);
        }

        if (pitches.size() == MAX_PITCHES_COUNT) {
            validateThreePitches(pitches);
        }

        this.pitches = pitches;
    }

    private void validateTwoPitches(final List<Pitch> pitches) {
        final Pitch firstPitch = pitches.get(0);
        final Pitch secondPitch = pitches.get(1);

        if (firstPitch.knockedPinsCount() < STRIKE_PINS_COUNT &&
                knockedPinsCount(Arrays.asList(firstPitch, secondPitch)) > STRIKE_PINS_COUNT) {
            throw new IllegalArgumentException(WRONG_KNOCKED_PINS_COUNT_MESSAGE);
        }
    }

    private void validateThreePitches(final List<Pitch> pitches) {
        final Pitch firstPitch = pitches.get(0);
        final Pitch secondPitch = pitches.get(1);
        final Pitch thirdPitch = pitches.get(2);

        if (firstPitch.knockedPinsCount() < STRIKE_PINS_COUNT &&
                knockedPinsCount(Arrays.asList(firstPitch, secondPitch)) < STRIKE_PINS_COUNT) {
            throw new IllegalArgumentException(WRONG_KNOCKED_PINS_COUNT_MESSAGE);
        }

        if (firstPitch.knockedPinsCount() == STRIKE_PINS_COUNT) {
            validateTwoPitches(Arrays.asList(secondPitch, thirdPitch));
        }
    }

    public static LastPitches init() {
        return new LastPitches(new ArrayList<>());
    }

    @Override
    public boolean playing() {
        return pitches.size() < MAX_PITCHES_COUNT - 1 ||
                (pitches.size() == MAX_PITCHES_COUNT - 1 && strikeOrSpare(pitches));
    }

    private boolean strikeOrSpare(final List<Pitch> pitches) {
        final Pitch firstPitch = pitches.get(0);
        final Pitch secondPitch = pitches.get(1);

        return firstPitch.knockedPinsCount() == STRIKE_PINS_COUNT ||
                knockedPinsCount(Arrays.asList(firstPitch, secondPitch)) == STRIKE_PINS_COUNT;
    }

    @Override
    public Pitches play(final Pitch pitch) {
        return new LastPitches(concat(pitches, pitch));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastPitches that = (LastPitches) o;
        return Objects.equals(pitches, that.pitches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pitches);
    }

    @Override
    public Iterator<Pitch> iterator() {
        return pitches.iterator();
    }

    @Override
    public void forEach(Consumer<? super Pitch> action) {
        pitches.forEach(action);
    }

    @Override
    public Spliterator<Pitch> spliterator() {
        return pitches.spliterator();
    }
}
