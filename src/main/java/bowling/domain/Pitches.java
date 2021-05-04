package bowling.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Pitches {
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
}