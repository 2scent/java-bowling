package bowling.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FrameTest {
    @Test
    public void first() {
        final Frame frame = Frame.first();
        assertThat(frame.last()).isFalse();
    }

    @Test
    public void next() {
        Frame frame = Frame.first();

        for (int i = 0; i < 9; i++) {
            assertThat(frame.last()).isFalse();

            frame = frame.play(new Pitch(10));
            assertThat(frame.playing()).isFalse();

            frame = frame.next();
        }

        assertThat(frame.last()).isTrue();

        frame = frame.play(new Pitch(10));
        assertThat(frame.playing()).isTrue();
    }


    @Test
    public void invalid() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Frame frame = Frame.first();

            while (!frame.last()) {
                frame = frame.next();
            }

            frame.next();
        });
    }
}
