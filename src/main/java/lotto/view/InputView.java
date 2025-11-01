package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.*;

public class InputView {
    // 검증로직 분리하여 메서드 길이 줄이기
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

    public List<Integer> inputWinningNumbers(){
        String winningNumbers = Console.readLine();
        // 검증 메서드 추후 구현
        try {
            return Arrays.stream(winningNumbers.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }

    public int inputBonusNumber(){
        try{
            int bonusNumber = Integer.parseInt(Console.readLine());
            // 검증 메서드 추후 구현
            return bonusNumber;
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }
}
