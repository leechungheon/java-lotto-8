package lotto.util;

import java.util.Arrays;
import java.util.List;

public class LottoParser {
    public static List<Integer> parseWinningNumbers(String input){
        List<Integer> winningNumbers = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(LottoParser::parseInt)
                .toList();
        InputValidator.validateInputWinningNumbers(winningNumbers);
        return winningNumbers;
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자로만 입력해야 합니다.");
        }
    }
}
