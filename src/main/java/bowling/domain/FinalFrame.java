package bowling.domain;

import java.util.Collections;
import java.util.List;

public final class FinalFrame extends DefaultFrame {
    private static final String FIRST_STRIKE_OR_SECOND_SPARE_MESSAGE =
            "세 번째 투구를 진행하기 위해서는 첫 번째 투구가 스트라이크이거나 두 번째 투구가 스페어야 합니다.";

    private FinalFrame(final List<Pitch> pitches) {
        super(pitches);
    }

    public static DefaultFrame init() {
        return new FinalFrame(Collections.emptyList());
    }

    @Override
    public boolean playing() {
        return !isPitchesFull() && (pitches().size() < maxPitchesCount() - 1 || isFirstStrike() || isSecondSpare());
    }

    @Override
    public DefaultFrame play(final int knockedPinsCount) {
        return play(KnockedPins.from(knockedPinsCount));
    }

    @Override
    public DefaultFrame play(final KnockedPins knockedPins) {
        validatePitchesFull();
        validateFirstStrikeOrSecondSpare();

        return new FinalFrame(playedPitches(knockedPins));
    }

    private void validateFirstStrikeOrSecondSpare() {
        if (pitches().size() == maxPitchesCount() - 1 && !isFirstStrike() && !isSecondSpare()) {
            throw new IllegalArgumentException(FIRST_STRIKE_OR_SECOND_SPARE_MESSAGE);
        }
    }

    @Override
    public DefaultFrame next() {
        throw new IllegalArgumentException();
    }

    @Override
    public DefaultFrame last() {
        throw new IllegalArgumentException();
    }

    @Override
    protected int maxPitchesCount() {
        return 3;
    }

    @Override
    public boolean calculableScore(List<Frame> frames) {
        return !playing();
    }

    @Override
    public int score(List<Frame> frames) {
        return pitches().stream()
                .map(Pitch::knockedPins)
                .map(KnockedPins::count)
                .reduce(0, Integer::sum);
    }

    @Override
    public Score calculateAdditionalScore(Score beforeScore, List<Frame> frames) {
        for (Pitch pitch : pitches()) {
            if (beforeScore.calculable()) {
                break;
            }
            beforeScore = beforeScore.bowl(pitch.knockedPins().count());
        }

        return beforeScore;
    }
}
