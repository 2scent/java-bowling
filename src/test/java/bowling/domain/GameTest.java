package bowling.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class GameTest {
    @Test
    public void init() {
        final Game bowling = Game.init();
        assertThat(bowling.playing()).isTrue();
    }

    @Test
    public void play() {
        Game bowling = Game.init();

        while (bowling.playing()) {
            bowling = bowling.play(10);
        }

        assertThat(bowling.frames().size()).isEqualTo(10);
        assertThat(bowling.frames().get(9)).isInstanceOf(FinalFrame.class);
    }

    @Test
    public void play_invalid() {
        assertThatIllegalStateException().isThrownBy(() -> {
            Game bowling = Game.init();

            while (bowling.playing()) {
                bowling = bowling.play(10);
            }

            bowling.play(10);
        });
    }
}
