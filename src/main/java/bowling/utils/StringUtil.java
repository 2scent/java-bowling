package bowling.utils;

public class StringUtil {
    private static final char DEFAULT_PAD = ' ';

    public static String center(final String s, final int size) {
        return center(s, size, DEFAULT_PAD);
    }

    public static String center(final String s, final int size, final char pad) {
        if (s == null || size <= s.length()) {
            return s;
        }

        final StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }

        sb.append(s);

        while (sb.length() < size) {
            sb.append(pad);
        }

        return sb.toString();
    }
}
