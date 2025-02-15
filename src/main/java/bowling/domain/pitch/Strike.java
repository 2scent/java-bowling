package bowling.domain.pitch;

import bowling.domain.KnockedPins;

public final class Strike implements Pitch {
    private static final int STRIKE_PINS_COUNT = 10;

    @Override
    public KnockedPins knockedPins() {
        return KnockedPins.from(STRIKE_PINS_COUNT);
    }

    @Override
    public Pitch play(final int knockedPinsCount) {
        return play(KnockedPins.from(knockedPinsCount));
    }

    @Override
    public Pitch play(final KnockedPins knockedPins) {
        if (knockedPins.count() == STRIKE_PINS_COUNT) {
            return new Strike();
        }

        return new Normal(knockedPins);
    }
}
