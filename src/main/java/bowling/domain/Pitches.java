package bowling.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Pitches implements Iterable<Pitch> {
    protected List<Pitch> pitches;

    public abstract boolean playing();

    public abstract Pitches play(Pitch pitch);

    protected int knockedPinsCount(final List<Pitch> pitches) {
        return pitches.stream()
                .map(Pitch::knockedPinsCount)
                .reduce(0, Integer::sum);
    }

    protected List<Pitch> concat(final List<Pitch> pitches, final Pitch pitch) {
        return Stream.concat(pitches.stream(), Stream.of(pitch))
                .collect(Collectors.toList());
    }

    public Pitch get(int index) {
        return pitches.get(index);
    }

    public int count() {
        return pitches.size();
    }

    public int knockedPinsCount() {
        return knockedPinsCount(pitches);
    }
}