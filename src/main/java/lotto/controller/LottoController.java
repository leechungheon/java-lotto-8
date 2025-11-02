package lotto.controller;

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
        // 로또 구입 금액 입력
        outputView.outputRequestPurchaseAmount();
        int purchaseAmount = inputView.inputPurchaseAmount();
        int purchaseCount = purchaseAmount/1000;

        // 로또 구매
        lottoService.purchaseLotto(purchaseCount);

        // 구매한 로또 출력
        LottosResponse lottosResponse = lottoService.getPurchasedLottosDto();
        outputView.outputPurchasedLottos(lottosResponse);

        // 당첨 번호 입력
        outputView.outputRequestWinningNumbers();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        // 보너스 번호 입력
        outputView.outputRequestBonusNumber();
        int bonusAmount = inputView.inputBonusNumber();

        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusAmount);

        // 당첨 통계 출력
        Map<Rank, Integer> statistics = lottoService.calculateWinningStatistics(winningLotto);
        double profitRate = lottoService.calculateProfitRate(statistics, purchaseAmount);

        outputView.outputWinningStatistics(statistics, profitRate);
    }
}
