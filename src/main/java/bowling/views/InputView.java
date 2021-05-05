package bowling.views;

import bowling.domain.PinsInput;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String name() {
        System.out.print("플레이어 이름은(3 english letters)?: ");
        return scanner.nextLine();
    }

    public static PinsInput pinsInput() {
        return frameIndex -> {
            System.out.printf("%d프레임 투구: ", frameIndex);
            final int result = scanner.nextInt();
            clearInputBuffer();
            return result;
        };
    }

    private static void clearInputBuffer() {
        scanner.nextLine();
    }
}
