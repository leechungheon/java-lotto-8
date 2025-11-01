package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public int inputPurchaseAmount(){
        try{
            int purchaseAmount = Integer.parseInt(Console.readLine());

            if(purchaseAmount<=0){
                throw new IllegalArgumentException("[ERROR] 0원 이하의 금액은 입력할 수 없습니다.");
            }
            if(purchaseAmount%1000 != 0){
                throw new IllegalArgumentException("[ERROR] 금액을 1,000원 단위로 입력해주세요.");
            }
            return purchaseAmount;
        }catch(NumberFormatException e){
           throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }
}
