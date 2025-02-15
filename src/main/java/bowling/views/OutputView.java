package bowling.views;

import bowling.domain.Bowling;
import bowling.domain.Game;
import bowling.domain.Player;
import bowling.domain.frame.Frame;
import bowling.domain.pitch.Pitch;
import bowling.domain.pitch.Spare;
import bowling.domain.pitch.Strike;
import bowling.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {
    private static final String DEFAULT_DIVIDER = "|";
    private static final int DEFAULT_LENGTH = 6;
    private static final int GUTTER_PINS_COUNT = 0;

    private static final String STRIKE_VIEW = "X";
    private static final String SPARE_VIEW = "/";
    private static final String GUTTER_VIEW = "-";

    public static void print(Bowling bowling) {
        printTitle();

        for (Player player : bowling.players()) {
            printFrames(player, player.frames());
            printScores(player.frames());
        }

        System.out.println();
    }

    public static void print(final Player player, final Game game) {
        printTitle();
        printFrames(player, game.frames());
        printScores(game.frames());
        System.out.println();
    }

    private static void printTitle() {
        final List<String> views = new ArrayList<>();

        views.add(StringUtil.center("NAME", DEFAULT_LENGTH));
        views.addAll(
                IntStream.rangeClosed(1, 10)
                        .mapToObj(i -> StringUtil.center(String.format("%02d", i), DEFAULT_LENGTH))
                        .collect(Collectors.toList())
        );

        System.out.println(withDivider(views));
    }

    private static void printFrames(final Player player, final List<Frame> frames) {
        final List<String> views = new ArrayList<>();

        views.add(StringUtil.center(player.name(), DEFAULT_LENGTH));
        views.addAll(
                frames.stream()
                        .map(OutputView::view)
                        .collect(Collectors.toList())
        );
        views.addAll(
                IntStream.rangeClosed(1, 10 - frames.size())
                        .mapToObj(i -> StringUtil.center("", DEFAULT_LENGTH))
                        .collect(Collectors.toList())
        );

        System.out.println(withDivider(views));
    }

    private static String view(final Frame frame) {
        final List<String> views = frame.pitches()
                .stream()
                .map(OutputView::view).collect(Collectors.toList());

        return StringUtil.center(String.join(DEFAULT_DIVIDER, views), DEFAULT_LENGTH);
    }

    private static String view(final Pitch pitch) {
        if (pitch instanceof Strike) {
            return STRIKE_VIEW;
        }

        if (pitch instanceof Spare) {
            return SPARE_VIEW;
        }

        if (pitch.knockedPins().count() == GUTTER_PINS_COUNT) {
            return GUTTER_VIEW;
        }

        return String.valueOf(pitch.knockedPins().count());
    }

    private static String withDivider(final List<String> views) {
        return DEFAULT_DIVIDER + String.join(DEFAULT_DIVIDER, views) + DEFAULT_DIVIDER;
    }

    private static void printScores(List<Frame> frames) {
        final List<String> views = new ArrayList<>();

        final List<Frame> calculableFrames = frames.stream()
                .filter(e -> e.score(frames).calculable())
                .collect(Collectors.toList());

        int totalScore = 0;

        views.add(StringUtil.center("", DEFAULT_LENGTH));

        for (Frame frame : calculableFrames) {
            totalScore += frame.score(frames).score();
            views.add(StringUtil.center(String.join(DEFAULT_DIVIDER, String.valueOf(totalScore)), DEFAULT_LENGTH));
        }

        views.addAll(
                IntStream.rangeClosed(1, 10 - calculableFrames.size())
                        .mapToObj(i -> StringUtil.center("", DEFAULT_LENGTH))
                        .collect(Collectors.toList())
        );

        System.out.println(withDivider(views));
    }
}