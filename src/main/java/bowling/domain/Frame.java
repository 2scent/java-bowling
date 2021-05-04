package bowling.domain;

public final class Frame {
    private static final int FIRST_FRAME = 1;
    private static final int LAST_FRAME = 10;

    private final int index;
    private final Pitches pitches;

    public Frame(final int index, final Pitches pitches) {
        if (index > LAST_FRAME) {
            throw new IllegalArgumentException();
        }

        this.index = index;
        this.pitches = pitches;
    }

    public static Frame first() {
        return new Frame(FIRST_FRAME, new NormalPitches());
    }

    public boolean playing() {
        return pitches.playing();
    }

    public Frame play(final int knockedPinsCount) {
        return new Frame(index, pitches.play(knockedPinsCount));
    }

    public Frame next() {
        if (last()) {
            throw new IllegalArgumentException();
        }

        final int nextIndex = index + 1;

        if (nextIndex == LAST_FRAME) {
            return new Frame(nextIndex, new FinalPitches());
        }

        return new Frame(nextIndex, new NormalPitches());
    }

    public boolean last() {
        return index == LAST_FRAME;
    }

    public int index() {
        return index;
    }
}
