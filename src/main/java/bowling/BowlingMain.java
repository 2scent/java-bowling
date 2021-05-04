package bowling;

import bowling.domain.Bowling;

import java.util.Scanner;

public class BowlingMain {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        Bowling.of(
                (index) -> {
                    System.out.printf("%d 프레임 투구 : ", index);
                    final int result = scanner.nextInt();
                    scanner.nextLine();
                    return result;
                },
                (frames) -> {
                    System.out.println(frames.size());
                }
        );
    }
}
