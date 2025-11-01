package lotto.controller;

import java.util.List;
import lotto.model.WinningLotto;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoController(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }

    public void run(){
        outputView.outputRequestPurchaseAmount();
        int purchaseAmount = inputView.inputPurchaseAmount();

        outputView.outputRequestWinningNumbers();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        outputView.outputRequestBonusNumber();
        int bonusAmount = inputView.inputBonusNumber();

        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusAmount);

        for(int i = 0; i < purchaseAmount/1000; i++){
            lottoService.purchaseLotto();
        }
    }
}
