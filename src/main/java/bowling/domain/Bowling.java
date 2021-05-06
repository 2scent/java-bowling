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

        framesOutput.print(frames);

        Frame frame = Frame.first();
        frame = initBody(pinsInput, framesOutput, frames, frame);
        initLast(pinsInput, framesOutput, frames, frame);

        return new Bowling(frames);
    }

    private static Frame initBody(PinsInput pinsInput, FramesOutput framesOutput, List<Frame> frames, Frame frame) {
        while (!frame.last()) {
            frame = playedFrame(pinsInput, framesOutput, frames, frame);
            frames.add(frame);
            frame = frame.next();
        }
        return frame;
    }

    private static void initLast(PinsInput pinsInput, FramesOutput framesOutput, List<Frame> frames, Frame frame) {
        frames.add(playedFrame(pinsInput, framesOutput, frames, frame));
    }

    private static Frame playedFrame(PinsInput pinsInput, FramesOutput framesOutput, List<Frame> frames, Frame frame) {
        while (frame.playing()) {
            frame = frame.play(new Pitch(pinsInput.knockedPins(frame.index())));

            framesOutput.print(
                    Stream.concat(frames.stream(), Stream.of(frame))
                            .collect(Collectors.toList())
            );
        }

        return frame;
    }
}
