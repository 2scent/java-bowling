package bowling.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Bowling {
    final List<Frame> frames;

    private Bowling(final List<Frame> frames) {
        this.frames = frames;
    }

    public static Bowling from(final PinsInput pinsInput, final FramesOutput framesOutput) {
        final List<Frame> frames = new ArrayList<>();

        Frame frame = Frame.first();

        while (!frame.last() || frame.playing()) {
            frame = playedFrame(pinsInput, framesOutput, frames, frame);
            frames.add(frame);
            frame = frame.next();
        }

        return new Bowling(frames);
    }

    private static Frame playedFrame(PinsInput pinsInput, FramesOutput framesOutput, List<Frame> frames, Frame frame) {
        while (frame.playing()) {
            framesOutput.print(
                    Stream.concat(frames.stream(), Stream.of(frame))
                            .collect(Collectors.toList())
            );

            frame = frame.play(new Pitch(pinsInput.knockedPins(frame.index())));
        }

        return frame;
    }
}
