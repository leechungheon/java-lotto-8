package lotto.controller;

import static lotto.util.LottoConstants.PURCHASE_AMOUNT_UNIT;

import java.util.List;
import java.util.Map;
import lotto.dto.LottosResponse;
import lotto.model.Rank;
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
        int purchaseAmount = getPurchaseAmount();
        purchaseLottos(purchaseAmount);

        WinningLotto winningLotto = getWinningLotto();

        calculateAndOutputStatistics(winningLotto, purchaseAmount);
    }

    private int getPurchaseAmount() {
        while (true) {
            try {
                outputView.outputRequestPurchaseAmount();
                return inputView.inputPurchaseAmount();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void purchaseLottos(int purchaseAmount) {
        int purchaseCount = purchaseAmount / PURCHASE_AMOUNT_UNIT;

        lottoService.purchaseLotto(purchaseCount);

        LottosResponse lottosResponse = lottoService.getPurchasedLottosDto();
        outputView.outputPurchasedLottos(lottosResponse);
    }

    private WinningLotto getWinningLotto() {
        while (true) {
            try {
                outputView.outputRequestWinningNumbers();
                List<Integer> winningNumbers = inputView.inputWinningNumbers();

                outputView.outputRequestBonusNumber();
                int bonusAmount = inputView.inputBonusNumber();

                return new WinningLotto(winningNumbers, bonusAmount);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void calculateAndOutputStatistics(WinningLotto winningLotto, int purchaseAmount) {
        Map<Rank, Integer> statistics = lottoService.calculateWinningStatistics(winningLotto);
        double profitRate = lottoService.calculateProfitRate(statistics, purchaseAmount);

        outputView.outputWinningStatistics(statistics, profitRate);
    }
}
