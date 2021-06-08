package bowling.domain;

public class Score {
    private int score; // 현재까지 점수
    private int left; // 남은 시도 횟수

    public Score(int score, int left) {
        this.score = score;
        this.left = left;
    }

    public Score bowl(int countOfPins) {
        return new Score(score += countOfPins, left - 1);
    }

    public int score() {
        if (!calculable()) {
            throw new IllegalStateException();
        }
        return this.score;
    }

    public boolean calculable() {
        return left == 0;
    }
}
