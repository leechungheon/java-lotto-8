package lotto.controller;

import java.util.List;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }
    public void run(){
        outputView.outputRequestPurchaseAmount();
        int purchaseAmount = inputView.inputPurchaseAmount();

        outputView.outputRequestWinningNumbers();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        outputView.outputRequestBonusNumber();
        int bonusAmount = inputView.inputBonusNumber();
    }
}
