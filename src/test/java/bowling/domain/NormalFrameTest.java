package bowling.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class NormalFrameTest {
    @Test
    public void init() {
        final Frame frame = NormalFrame.init();
        assertThat(frame.playing()).isTrue();
    }

    @Test
    public void invalid_투구_2개_초과() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            final Frame frame = NormalFrame.init();

            frame.play(3)
                    .play(3)
                    .play(3);
        });
    }

    @Test
    public void invalid_스트라이크() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            final Frame frame = NormalFrame.init();

            frame.play(10)
                    .play(3);
        });
    }

    @Test
    public void play_일반() {
        Frame frame = NormalFrame.init();
        assertThat(frame.playing()).isTrue();

        frame = frame.play(7);
        assertThat(frame.playing()).isTrue();

        frame = frame.play(2);
        assertThat(frame.playing()).isFalse();
    }

    @Test
    public void play_스트라이크() {
        Frame frame = NormalFrame.init();
        assertThat(frame.playing()).isTrue();

        frame = frame.play(10);
        assertThat(frame.playing()).isFalse();
    }

    @Test
    public void next() {
        final Frame next = NormalFrame.init().next();

        assertThat(next).isInstanceOf(NormalFrame.class);
    }

    @Test
    public void last() {
        final Frame last = NormalFrame.init().last();

        assertThat(last).isInstanceOf(FinalFrame.class);
    }

    @Test
    public void calculableScore1() {
        Frame frame = NormalFrame.init();
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        frame = frame.play(7);
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        frame = frame.play(2);
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isTrue();
    }

    @Test
    public void calculableScore2() {
        Frame frame = NormalFrame.init();
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        frame = frame.play(7);
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        frame = frame.play(3);
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        Frame next = frame.next();
        assertThat(frame.calculableScore(Arrays.asList(frame, next))).isFalse();

        next = next.play(5);
        assertThat(frame.calculableScore(Arrays.asList(frame, next))).isTrue();
    }


    @Test
    public void calculableScore3() {
        Frame frame = NormalFrame.init();
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        frame = frame.play(10);
        assertThat(frame.calculableScore(Collections.singletonList(frame))).isFalse();

        Frame next = frame.next();
        assertThat(frame.calculableScore(Arrays.asList(frame, next))).isFalse();

        next = next.play(5);
        assertThat(frame.calculableScore(Arrays.asList(frame, next))).isFalse();

        next = next.play(5);
        assertThat(frame.calculableScore(Arrays.asList(frame, next))).isTrue();
    }

    @Test
    public void score1() {
        Frame frame = NormalFrame.init();
        frame = frame.play(7);
        frame = frame.play(2);

        assertThat(frame.score(null)).isEqualTo(9);
    }
}
