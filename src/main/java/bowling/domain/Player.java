package bowling.domain;

import java.util.List;

public class Player {
    private static final int MAX_NAME_LENGTH = 3;
    private static final String OVER_MAX_NAME_LENGTH_MESSAGE =
            String.format("이름은 %d글자까지 가능합니다.", MAX_NAME_LENGTH);

    private final String name;

    private final Game game;

    public Player(String name) {
        this(name, Game.init());
    }

    private Player(String name, Game game) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(OVER_MAX_NAME_LENGTH_MESSAGE);
        }

        this.name = name;
        this.game = game;
    }

    public String name() {
        return name;
    }

    public boolean playing() {
        return game.playing();
    }

    public boolean playingFrame(int index) {
        if (game.frames().size() <= index) {
            return false;
        }

        return game.frames().get(index).playing();
    }

    public Player play(int knockedPinsCount) {
        return new Player(name, game.play(knockedPinsCount));
    }

    public List<Frame> frames() {
        return game.frames();
    }

    public boolean isCurrentFrame(int frameIndex) {
        return game.currentFrameIndex() == frameIndex;
    }

    public boolean isBeforeFrame(int frameIndex) {
        return game.currentFrameIndex() < frameIndex;
    }
}