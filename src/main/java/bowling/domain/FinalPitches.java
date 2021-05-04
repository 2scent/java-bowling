package bowling.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FinalPitches extends Pitches {
    private static final int MAX_PITCHES_COUNT = 3;
    private static final int MAX_KNOCKED_PINS_COUNT = 30;

    private final List<Pitch> pitches;

    public FinalPitches() {
        this(new ArrayList<>());
    }

    public FinalPitches(final List<Pitch> pitches) {
        if (pitches.size() > MAX_PITCHES_COUNT) {
            throw new IllegalArgumentException("공은 3번까지 던질 수 있어");
        }

        if (knockedPinsCount(pitches) > MAX_KNOCKED_PINS_COUNT) {
            throw new IllegalArgumentException("합계가 30은 넘을 수 없어");
        }

        this.pitches = pitches;
    }

    @Override
    public boolean playing() {
        return (pitches.size() < MAX_PITCHES_COUNT - 1) ||
                (pitches.size() == MAX_PITCHES_COUNT - 1 && knockedPinsCount(pitches) >= 10);
    }

    @Override
    public Pitches play(final int knockedPinsCount) {
        return new FinalPitches(concat(pitches, knockedPinsCount));
    }
}
