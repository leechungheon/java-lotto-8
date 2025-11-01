package lotto.model;

import java.util.List;

public class WinningLotto {
    private final List<Integer> numbers;
    private final int bonusNumber;

    public WinningLotto(List<Integer> numbers,  int bonusNumber) {
        validate(numbers,bonusNumber);
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    private void validate(List<Integer> numbers,  int bonusNumber){
        for(Integer number : numbers){
            if(number == bonusNumber){
                throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호와 중복될 수 없습니다.");
            }
        }
    }
}
