package bowling.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Bowling {
    final List<Frame> frames;

    private Bowling(final List<Frame> frames) {
        this.frames = frames;
    }

    public static Bowling of(final Function<Integer, Integer> input, final Consumer<List<Frame>> output) {
        final List<Frame> frames = new ArrayList<>();

        Frame frame = Frame.first();

        while (!frame.last() || frame.playing()) {
            while (frame.playing()) {
                output.accept(
                        Stream.concat(frames.stream(), Stream.of(frame))
                                .collect(Collectors.toList())
                );

                frame = frame.play(input.apply(frame.index()));
            }

            frames.add(frame);

            frame = frame.next();
        }

        return new Bowling(frames);
    }
}
