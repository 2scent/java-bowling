package bowling.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class NormalPitches extends Pitches {
    private static final int MAX_PITCHES_COUNT = 2;
    private static final int MAX_KNOCKED_PINS_COUNT = 10;

    private final List<Pitch> pitches;

    public NormalPitches() {
        this(new ArrayList<>());
    }

    public NormalPitches(final List<Pitch> pitches) {
        if (pitches.size() > MAX_PITCHES_COUNT) {
            throw new IllegalArgumentException("공은 2번까지 던질 수 있어");
        }

        if (knockedPinsCount(pitches) > MAX_KNOCKED_PINS_COUNT) {
            throw new IllegalArgumentException("합계가 10은 넘을 수 없어");
        }

        this.pitches = pitches;
    }

    @Override
    public boolean playing() {
        return pitches.size() < MAX_PITCHES_COUNT && knockedPinsCount(pitches) < MAX_KNOCKED_PINS_COUNT;
    }

    @Override
    public Pitches play(final int knockedPinsCount) {
        return new NormalPitches(concat(pitches, knockedPinsCount));
    }
}
