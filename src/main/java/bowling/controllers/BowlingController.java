package bowling.controllers;

import bowling.domain.Bowling;
import bowling.domain.Player;
import bowling.views.InputView;
import bowling.views.OutputView;

import java.util.ArrayList;
import java.util.List;

public class BowlingController {
    private static final int number = 2;

    public static void run() {
        final List<Player> players = new ArrayList<>();
        final List<Bowling> bowlingList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            players.add(new Player(InputView.name()));
        }

        for (int i = 0; i < number; i++) {
            bowlingList.add(Bowling.init());
        }

        OutputView.print(players, bowlingList);


        int frameIndex = 1;
        while (bowlingList.stream().anyMatch(Bowling::playing)) {
            for (int i = 0; i < number; i++) {
                Bowling bowling = bowlingList.get(i);

                do {
                    bowling = bowling.play(InputView.knockedPinsCount(bowling.currentFrameIndex()));
                    bowlingList.set(i, bowling);
                    OutputView.print(players, bowlingList);
                } while (bowling.playingFrame(frameIndex));
            }

            frameIndex++;
        }
    }

//    public static void run() {
//        final Player player = new Player(InputView.name());
//
//        Bowling bowling = Bowling.init();
//        OutputView.print(player, bowling);
//
//        while (bowling.playing()) {
//            bowling = bowling.play(InputView.knockedPinsCount(bowling.currentFrameIndex()));
//            OutputView.print(player, bowling);
//        }
//    }
}