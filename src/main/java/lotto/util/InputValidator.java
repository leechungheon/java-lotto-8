package lotto.util;


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
}
