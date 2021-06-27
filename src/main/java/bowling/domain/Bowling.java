package bowling.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Bowling {
    final List<Player> players;

    final int currentFrame;

    public Bowling(List<Player> players) {
        this(players, 1);
    }

    private Bowling(List<Player> players, int currentFrame) {
        this.players = players;
        this.currentFrame = currentFrame;
    }

    public boolean playing() {
        return players.stream()
                .anyMatch(Player::playing);
    }

    public Player currentPlayer() {
        System.out.println(currentFrame);

        Player currentPlayer = players.stream()
                .filter(player -> player.isCurrentFrame(currentFrame))
                .filter(player -> player.playingFrame(currentFrame - 1))
                .findFirst()
                .orElse(null);

        if (currentPlayer != null) return currentPlayer;

        currentPlayer = players.stream()
                .filter(player -> player.isBeforeFrame(currentFrame))
                .findFirst()
                .orElse(null);

        return currentPlayer;
    }

    public Bowling play(int knockedPinsCount) {
        final List<Player> newPlayers = players.stream()
                .map(player -> player.equals(currentPlayer()) ? player.play(knockedPinsCount) : player)
                .collect(Collectors.toList());

        return new Bowling(
                newPlayers,
                newPlayers.stream()
                        .anyMatch(player -> (player.isCurrentFrame(currentFrame) && player.playingFrame(currentFrame - 1)) || player.isBeforeFrame(currentFrame)) ? currentFrame : currentFrame + 1
        );
    }

    public List<Player> players() {
        return Collections.unmodifiableList(players);
    }
}
