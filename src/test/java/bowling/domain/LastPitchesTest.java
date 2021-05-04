package bowling.domain;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

public class LastPitchesTest {
    @Test
    public void init() {
        final Pitches pitches = LastPitches.init();
        assertThat(pitches.playing()).isTrue();
    }

    @Test
    @DisplayName("투구가 3개를 초과")
    public void invalid1() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            final Pitches pitches = LastPitches.init();
            pitches.play(new Pitch(10))
                    .play(new Pitch(10))
                    .play(new Pitch(10))
                    .play(new Pitch(10));
        });
    }

    @Test
    @DisplayName("첫 번째 투구가 스트라이크가 아니고, 첫 번째 투구와 두 번째 투구의 합이 10을 초과")
    public void invalid2() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            final Pitches pitches = LastPitches.init();
            pitches.play(new Pitch(6))
                    .play(new Pitch(5));
        });
    }

    @Test
    @DisplayName("첫 번째 투구가 스트라이크가 아니고, 두 번째 투구까지도 스페어가 아닌 경우")
    public void invalid3() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            final Pitches pitches = LastPitches.init();
            pitches.play(new Pitch(3))
                    .play(new Pitch(3))
                    .play(new Pitch(3));
        });
    }

    @Test
    @DisplayName("첫 번째 투구가 스트라이크고, 두 번째와 세 번째 투구의 합이 10을 초과")
    public void invalid4() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            final Pitches pitches = LastPitches.init();
            pitches.play(new Pitch(10))
                    .play(new Pitch(4))
                    .play(new Pitch(8));
        });
    }

    @Test
    @DisplayName("두 번째 투구까지 스페어고, 두 번째 투구와 세 번째 투구가 10을 초과")
    public void valid() {
        assertThatCode(() -> {
            final Pitches pitches = LastPitches.init();
            pitches.play(new Pitch(8))
                    .play(new Pitch(2))
                    .play(new Pitch(10));
        }).doesNotThrowAnyException();
    }

    @Test
    public void play_스트라이크() {
        Pitches pitches = LastPitches.init();
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(10));
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(8));
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(2));
        assertThat(pitches.playing()).isFalse();
    }

    @Test
    public void play_스페어() {
        Pitches pitches = LastPitches.init();
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(8));
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(2));
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(10));
        assertThat(pitches.playing()).isFalse();
    }

    @Test
    public void play_미스() {
        Pitches pitches = LastPitches.init();
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(8));
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(1));
        assertThat(pitches.playing()).isFalse();
    }
}
