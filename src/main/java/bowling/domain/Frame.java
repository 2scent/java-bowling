package bowling.domain;

public final class Frame {
    private static final int FIRST_FRAME_INDEX = 1;
    private static final int LAST_FRAME_INDEX = 10;
    private static final String OVER_LAST_FRAME_MESSAGE = "프레임을 초과했습니다.";

    private final int index;
    private final Pitches pitches;

    private Frame(final int index, final Pitches pitches) {
        if (index > LAST_FRAME_INDEX) {
            throw new IllegalArgumentException(OVER_LAST_FRAME_MESSAGE);
        }

        this.index = index;
        this.pitches = pitches;
    }

    public static Frame first() {
        return new Frame(FIRST_FRAME_INDEX, NormalPitches.init());
    }

    public boolean playing() {
        return pitches.playing();
    }

    public Frame play(final Pitch pitch) {
        return new Frame(index, pitches.play(pitch));
    }

    public boolean last() {
        return index == LAST_FRAME_INDEX;
    }

    public Frame next() {
        final int nextIndex = index + 1;

        if (nextIndex == LAST_FRAME_INDEX) {
            return new Frame(nextIndex, LastPitches.init());
        }

        return new Frame(nextIndex, NormalPitches.init());
    }
}
