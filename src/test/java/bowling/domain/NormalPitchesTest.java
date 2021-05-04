package bowling.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class NormalPitchesTest {
    @Test
    public void init() {
        final Pitches pitches = NormalPitches.init();
        assertThat(pitches.playing()).isTrue();
    }

    @Test
    public void invalid_투구_2개_초과() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Pitches pitches = NormalPitches.init();
            pitches.play(new Pitch(3))
                    .play(new Pitch(3))
                    .play(new Pitch(3));
        });
    }

    @Test
    public void invalid_핀_10개_초과() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Pitches pitches = NormalPitches.init();
            pitches.play(new Pitch(10))
                    .play(new Pitch(5));
        });
    }

    @Test
    public void play_일반() {
        Pitches pitches = NormalPitches.init();
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(7));
        assertThat(pitches.playing()).isTrue();


        pitches = pitches.play(new Pitch(2));
        assertThat(pitches.playing()).isFalse();
    }

    @Test
    public void play_스트라이크() {
        Pitches pitches = NormalPitches.init();
        assertThat(pitches.playing()).isTrue();

        pitches = pitches.play(new Pitch(10));
        assertThat(pitches.playing()).isFalse();
    }
}
