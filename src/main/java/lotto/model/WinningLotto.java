package lotto.model;

import java.util.List;

public class WinningLotto {
    private final List<Integer> numbers;

    public WinningLotto(List<Integer> numbers) {
        validateInputWinningNumbers(numbers);
        this.numbers = numbers;
    }

    private void validateInputWinningNumbers(List<Integer> winningNumbers) {
        validateLottoSize(winningNumbers);
        validateLottoRange(winningNumbers);
        validateLottoDuplication(winningNumbers);
    }

    private void validateLottoSize(List<Integer> lottoNumbers) {
        if (lottoNumbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    private void validateLottoRange(List<Integer> winningNumbers) {
        for (Integer winningNumber : winningNumbers) {
            if (winningNumber <= 0 || winningNumber > 45) {
                throw new IllegalArgumentException("[ERROR] 당첨 번호는 1이상 45이하의 숫자가 되어야 합니다.");
            }
        }
    }

    private void validateLottoDuplication(List<Integer> winningNumbers) {
        long distinctCount = winningNumbers.stream().distinct().count();
        if (distinctCount != winningNumbers.size()) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 서로 중복될 수 없습니다.");
        }
    }
}
