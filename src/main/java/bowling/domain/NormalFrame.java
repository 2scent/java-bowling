package bowling.domain;

import java.util.Collections;
import java.util.List;

public final class NormalFrame extends DefaultFrame {
    private static final String FIRST_STRIKE_MESSAGE = "첫 번재 투구가 스트라이크인 경우, 두 번째 투구를 진행할 수 없습니다.";

    private NormalFrame(final List<Pitch> pitches) {
        super(pitches);
    }

    public static DefaultFrame init() {
        return new NormalFrame(Collections.emptyList());
    }

    @Override
    public boolean playing() {
        return !isPitchesFull() && !isFirstStrike();
    }

    @Override
    public DefaultFrame play(final int knockedPinsCount) {
        return play(KnockedPins.from(knockedPinsCount));
    }

    @Override
    public DefaultFrame play(final KnockedPins knockedPins) {
        validatePitchesFull();
        validateFirstStrike();

        return new NormalFrame(playedPitches(knockedPins));
    }

    private void validateFirstStrike() {
        if (isFirstStrike()) {
            throw new IllegalArgumentException(FIRST_STRIKE_MESSAGE);
        }
    }

    @Override
    public DefaultFrame next() {
        return NormalFrame.init();
    }

    @Override
    public DefaultFrame last() {
        return FinalFrame.init();
    }

    @Override
    protected int maxPitchesCount() {
        return 2;
    }

    @Override
    public boolean calculableScore(List<Frame> frames) {
        return getScore(frames).calculable();
    }

    @Override
    public int score(List<Frame> frames) {
        return getScore(frames).score();
    }

    private Score getScore(List<Frame> frames) {
        Score score;

        if (isFirstStrike()) {
            score = new Score(10, 2);
        } else if (isSecondSpare()) {
            score = new Score(10, 1);
        } else {
            score = new Score(
                    pitches().stream()
                            .map(Pitch::knockedPins)
                            .map(KnockedPins::count)
                            .reduce(0, Integer::sum),
                    maxPitchesCount() - pitches().size()
            );
        }

        if (score.calculable()) {
            return score;
        }

        Frame nextFrame = nextFrame(frames);

        if (nextFrame != null) {
            return nextFrame.calculateAdditionalScore(score, frames);
        }

        return score;
    }

    @Override
    public Score calculateAdditionalScore(Score beforeScore, List<Frame> frames) {
        for (Pitch pitch : pitches()) {
            if (beforeScore.calculable()) {
                break;
            }
            beforeScore = beforeScore.bowl(pitch.knockedPins().count());
        }

        if (beforeScore.calculable()) {
            return beforeScore;
        }

        Frame nextFrame = nextFrame(frames);

        if (nextFrame != null) {
            return nextFrame.calculateAdditionalScore(beforeScore, frames);
        }

        return beforeScore;
    }

    private Frame nextFrame(List<Frame> frames) {
        try {
            return frames.get(frames.indexOf(this) + 1);
        } catch (Exception e) {
            return null;
        }
    }
}
