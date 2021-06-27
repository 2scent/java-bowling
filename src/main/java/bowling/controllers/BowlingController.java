package bowling.controllers;

import bowling.domain.Bowling;
import bowling.domain.Game;
import bowling.domain.Player;
import bowling.views.InputView;
import bowling.views.OutputView;

import java.util.ArrayList;
import java.util.List;

public class BowlingController {
//    public static void run() {
//        final Player player = new Player(InputView.name());
//
//        Game game = Game.init();
//        OutputView.print(player, game);
//
//        while (game.playing()) {
//            game = game.play(InputView.knockedPinsCount(game.currentFrameIndex()));
//            OutputView.print(player, game);
//        }
//    }

    public static void run() {
        Bowling bowling = new Bowling(players(InputView.playersCount()));
        OutputView.print(bowling);

        while (bowling.playing()) {
            bowling = bowling.play(InputView.knockedPinsCount(bowling.currentPlayer()));
            OutputView.print(bowling);
        }
    }

    public static List<Player> players(int count) {
        final List<Player> players = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            players.add(new Player(InputView.name(i)));
        }

        return players;
    }
}