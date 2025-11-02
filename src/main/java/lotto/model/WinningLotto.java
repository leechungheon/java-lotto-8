package lotto.model;

import java.util.List;
import java.util.Objects;

public class WinningLotto {
    private final List<Integer> numbers;
    private final int bonusNumber;

    public WinningLotto(List<Integer> numbers,  int bonusNumber) {
        validate(numbers,bonusNumber);
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    public int countMatch(Lotto purchasedLotto) {
        int matchCount = 0;
        List<Integer> winningNumbers = this.numbers;

        for (Integer purchasedNumber : purchasedLotto.getNumbers()) {
            if (winningNumbers.contains(purchasedNumber)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    public boolean containsBonus(Lotto purchasedLotto){
        for (Integer purchasedNumber : purchasedLotto.getNumbers()) {
            if (bonusNumber==purchasedNumber) {
                return true;
            }
        }
        return false;
    }

    private void validate(List<Integer> numbers,  int bonusNumber){
        for(Integer number : numbers){
            if(number == bonusNumber){
                throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호와 중복될 수 없습니다.");
            }
        }
    }
}
