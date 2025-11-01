package lotto.util;

import java.util.List;

public class InputValidator {
    private static final int PURCHASE_AMOUNT_UNIT = 1000;
    public static void validateInputPurchaseAmount(int purchaseAmount){
        if(purchaseAmount<=0){
            throw new IllegalArgumentException("[ERROR] 0원 이하의 금액은 입력할 수 없습니다.");
        }
        if(purchaseAmount % PURCHASE_AMOUNT_UNIT != 0){
            throw new IllegalArgumentException("[ERROR] 금액을 1,000원 단위로 입력해주세요.");
        }
    }

    public static void validateInputWinningNumbers(List<Integer> winningNumbers){
        if(winningNumbers.size()!=6){
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개의 숫자를 입력해야 합니다.");
        }
        for(Integer winningNumber : winningNumbers){
            if(winningNumber<=0 || winningNumber > 45){
                throw new IllegalArgumentException("[ERROR] 당첨 번호는 1이상 45이하의 숫자가 되어야 합니다.");
            }
        }
    }
}
