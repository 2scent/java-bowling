package bowling.domain;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class PitchTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 10})
    public void create(final int knockedPinsCount) {
        assertThatCode(() -> new Pitch(knockedPinsCount)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    public void create_invalid(final int knockedPinsCount) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Pitch(knockedPinsCount));
    }

    @Test
    public void gutter_참() {
        final Pitch pitch = new Pitch(0);

        assertThat(pitch.gutter()).isTrue();
    }

    @Test
    public void gutter_거짓() {
        final Pitch pitch = new Pitch(5);

        assertThat(pitch.gutter()).isFalse();
    }
}