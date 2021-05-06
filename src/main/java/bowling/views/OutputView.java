package bowling.views;

import bowling.domain.*;
import bowling.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {
    private static final String DIVIDER = "|";
    private static final int SIZE = 6;
    private static final int DEFAULT_PINS_COUNT = 10;

    private static final String STRIKE_MARK = "X";
    private static final String SPARE_MARK = "/";
    private static final String GUTTER_MARK = "-";

    public static FramesOutput framesOutput(final Player player) {
        return frames -> {
            printTitle();
            print(player, frames);
            System.out.println();
        };
    }

    private static void printTitle() {
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s%s%s", DIVIDER, StringUtil.center("NAME", SIZE), DIVIDER));
        sb.append(
                IntStream.rangeClosed(1, 10)
                        .mapToObj(i -> StringUtil.center(String.format("%02d", i), SIZE))
                        .collect(Collectors.joining(DIVIDER))
        );
        sb.append(DIVIDER);

        System.out.println(sb);
    }

    private static void print(final Player player, final List<Frame> frames) {
        final StringBuilder sb = new StringBuilder();

        sb.append(view(player));
        sb.append(
                frames.stream()
                        .map(OutputView::view)
                        .collect(Collectors.joining(DIVIDER))
        );

        if (frames.size() > 0) {
            sb.append(DIVIDER);
        }

        sb.append(
                IntStream.rangeClosed(1, 10 - frames.size())
                        .mapToObj(i -> StringUtil.center("", SIZE))
                        .collect(Collectors.joining(DIVIDER))
        );

        if (frames.size() < 10) {
            sb.append(DIVIDER);
        }

        System.out.println(sb);
    }

    private static String view(final Player player) {
        return String.format("%s%s%s", DIVIDER, StringUtil.center(player.name(), SIZE), DIVIDER);
    }

    private static String view(final Frame frame) {
        final List<String> views = new ArrayList<>();

        int remainingPinsCount = DEFAULT_PINS_COUNT;

        for (final Pitch pitch : frame.pitches()) {
            views.add(view(pitch, remainingPinsCount));

            remainingPinsCount = remainingPinsCount == pitch.knockedPinsCount() ?
                    DEFAULT_PINS_COUNT : remainingPinsCount - pitch.knockedPinsCount();
        }

        return StringUtil.center(String.join(DIVIDER, views), SIZE);
    }

    private static String view(final Pitch pitch, final int remainingPinsCount) {
        if (pitch.knockedPinsCount() == DEFAULT_PINS_COUNT) {
            return STRIKE_MARK;
        }

        if (pitch.knockedPinsCount() == remainingPinsCount) {
            return SPARE_MARK;
        }

        if (pitch.gutter()) {
            return GUTTER_MARK;
        }

        return String.valueOf(pitch.knockedPinsCount());
    }
}
