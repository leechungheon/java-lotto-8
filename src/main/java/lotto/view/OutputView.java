package lotto.view;

public class OutputView {
    public void outputRequestPurchaseAmount(){
        System.out.println("구입금액을 입력해 주세요.");
    }
    public void outputPurchaseCount(int count){
        System.out.println("\n"+count+"개를 구매했습니다.");
    }

    public void outputRequestWinningNumbers(){
        System.out.println("\n당첨 번호를 입력해 주세요.");
    }

    public void outputRequestBonusNumber(){
        System.out.println("\n보너스 번호를 입력해 주세요.");
    }
}
