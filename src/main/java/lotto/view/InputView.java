package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.*;
import lotto.util.InputValidator;
import lotto.util.LottoParser;

public class InputView {
    public int inputPurchaseAmount(){
        try{
            int purchaseAmount = Integer.parseInt(Console.readLine());
            InputValidator.validateInputPurchaseAmount(purchaseAmount);
            return purchaseAmount;
        }catch(NumberFormatException e){
           throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }

    public List<Integer> inputWinningNumbers(){
        String winningNumbers = Console.readLine();
        return LottoParser.parseWinningNumbers(winningNumbers);
    }

    public int inputBonusNumber(){
        try{
            int bonusNumber = Integer.parseInt(Console.readLine());
            InputValidator.validateBonusNumberRange(bonusNumber); // 단일 유효성 검증만 진행
            // 당첨 번호와의 '중복' 검증은 WinningLotto 객체 생성 시점에서 진행
            return bonusNumber;
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }
}
