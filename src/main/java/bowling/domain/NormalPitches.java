package bowling.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class NormalPitches extends Pitches {
    private static final int MAX_PITCHES_COUNT = 2;
    private static final String OVER_MAX_PITCHES_COUNT_MESSAGE =
            String.format("pitches.size()는 %d 이하여야 합니다.", MAX_PITCHES_COUNT);
    private static final int MAX_KNOCKED_PINS_COUNT = 10;
    private static final String OVER_MAX_KNOCKED_PINS_COUNT_MASSAGE =
            String.format("모든 투구들의 넘어뜨린 핀 개수의 합은 %d 이하여야 합니다.", MAX_KNOCKED_PINS_COUNT);

    private final List<Pitch> pitches;

    private NormalPitches(final List<Pitch> pitches) {
        if (pitches.size() > MAX_PITCHES_COUNT) {
            throw new IllegalArgumentException(OVER_MAX_PITCHES_COUNT_MESSAGE);
        }

        if (knockedPinsCount(pitches) > MAX_KNOCKED_PINS_COUNT) {
            throw new IllegalArgumentException(OVER_MAX_KNOCKED_PINS_COUNT_MASSAGE);
        }

        this.pitches = pitches;
    }

    public static NormalPitches init() {
        return new NormalPitches(new ArrayList<>());
    }

    @Override
    public boolean playing() {
        return pitches.size() < MAX_PITCHES_COUNT && knockedPinsCount(pitches) < MAX_KNOCKED_PINS_COUNT;
    }

    @Override
    public Pitches play(final Pitch pitch) {
        return new NormalPitches(concat(pitches, pitch));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalPitches that = (NormalPitches) o;
        return Objects.equals(pitches, that.pitches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pitches);
    }
}
