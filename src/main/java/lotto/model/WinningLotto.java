package lotto.model;

import java.util.List;

public class WinningLotto {
    private final List<Integer> numbers;

    public WinningLotto(List<Integer> numbers) {
        //validateInputWinningNumbers(numbers);
        this.numbers = numbers;
    }
}
