package lotto.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.util.List;
import java.util.Map;
import lotto.dto.LottosResponse;
import lotto.model.Lotto;
import lotto.model.Lottos;
import lotto.model.Rank;
import lotto.model.WinningLotto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoServiceTest {

    private LottoService lottoService;

    private static final List<Integer> WIN_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    private static final int BONUS_NUMBER = 7;

    @BeforeEach
    void setUp() {
        lottoService = new LottoService();
    }

    private static class StubWinningLotto extends WinningLotto {

        public StubWinningLotto(List<Integer> numbers, int bonus) {
            super(numbers, bonus);
        }

        @Override
        public int countMatch(Lotto purchasedLotto) {
            int count = 0;
            for (Integer num : purchasedLotto.getNumbers()) {
                if (WIN_NUMBERS.contains(num)) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public boolean containsBonus(Lotto purchasedLotto) {
            return purchasedLotto.getNumbers().contains(BONUS_NUMBER);
        }
    }


    @DisplayName("구매 개수만큼 로또가 생성되고 DTO에 정확히 담긴다.")
    @Test
    void purchaseLotto_GeneratesCorrectCountAndMapsToDto() {
        int purchaseCount = 5;
        lottoService.purchaseLotto(purchaseCount);
        LottosResponse response = lottoService.getPurchasedLottosDto();
        assertThat(response.getLottoCount()).isEqualTo(purchaseCount);
    }


    @DisplayName("다양한 등수의 당첨 통계를 정확히 계산한다.")
    @Test
    void calculateWinningStatistics_CalculatesAllRanksCorrectly() {
        WinningLotto winningLotto = new StubWinningLotto(WIN_NUMBERS, BONUS_NUMBER);

        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),    // 1등 (6/0)
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),    // 2등 (5/1)
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),    // 3등 (5/0)
                new Lotto(List.of(1, 2, 3, 4, 10, 11)),  // 4등 (4/0)
                new Lotto(List.of(1, 2, 3, 10, 11, 12)), // 5등 (3/0)
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 낙첨 (0/0)
        );

        lottoService.purchasedLottos = new Lottos(purchasedLottos);
        Map<Rank, Integer> statistics = lottoService.calculateWinningStatistics(winningLotto);

        assertThat(statistics).containsEntry(Rank.FIRST, 1);
        assertThat(statistics).containsEntry(Rank.SECOND, 1);
        assertThat(statistics).containsEntry(Rank.THIRD, 1);
        assertThat(statistics).containsEntry(Rank.FOURTH, 1);
        assertThat(statistics).containsEntry(Rank.FIFTH, 1);
        assertThat(statistics).containsEntry(Rank.MISS, 1);
    }


    @DisplayName("수익률을 정확히 계산하고 반환한다.")
    @Test
    void calculateProfitRate_CalculatesCorrectRate() {
        int purchaseAmount = 10000;

        // 예상 수입: 5등(5000) 1개, 4등(50000) 1개, 3등(1,500,000) 1개
        long expectedRevenue = 5000L + 50000L + 1500000L;
        double expectedRate = (double)expectedRevenue / purchaseAmount * 100.0; // 15550.0%

        Map<Rank, Integer> rankingResults = Map.of(
                Rank.FIFTH, 1,
                Rank.FOURTH, 1,
                Rank.THIRD, 1,
                Rank.MISS, 7
        );

        double profitRate = lottoService.calculateProfitRate(rankingResults, purchaseAmount);
        assertThat(profitRate).isCloseTo(expectedRate, offset(0.001));
    }
}