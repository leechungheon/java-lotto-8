package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.dto.LottoDto;
import lotto.dto.LottosResponse;
import lotto.model.Lotto;
import lotto.model.Lottos;
import lotto.model.Rank;
import lotto.model.WinningLotto;

public class LottoService {
    private Lottos purchasedLottos;
    public void purchaseLotto(int lottoCount){
        List<Lotto> generatedLottos  = new ArrayList<>();
        for(int i = 0; i < lottoCount; i++){
            generatedLottos.add(new Lotto(Randoms.pickUniqueNumbersInRange(1, 45, 6)));
        }
        purchasedLottos = new Lottos(generatedLottos);
    }

    public LottosResponse getPurchasedLottosDto() {
        Lottos purchasedLottos = this.purchasedLottos;

        List<LottoDto> lottoDtos = purchasedLottos.getLottos().stream()
                .map(lotto -> new LottoDto(lotto.getNumbers()))
                .collect(Collectors.toList());

        return new LottosResponse(
                purchasedLottos.size(),
                lottoDtos
        );
    }


    public Map<Rank, Integer> calculateWinningStatistics(WinningLotto winningLotto) {
        Map<Rank, Integer> statistics = initializeStatistics();

        for (Lotto lotto : purchasedLottos.getLottos()) {

            int matchCount = winningLotto.countMatch(lotto);
            boolean matchBonus = winningLotto.containsBonus(lotto);

            Rank rank = Rank.valueOf(matchCount, matchBonus);

            statistics.put(rank, statistics.getOrDefault(rank, 0) + 1);
        }

        return statistics;
    }

    public double calculateProfitRate(Map<Rank, Integer> rankingResults, int purchaseAmount) {
        long totalRevenue = 0;

        for (Map.Entry<Rank, Integer> entry : rankingResults.entrySet()) {
            Rank rank = entry.getKey();
            int count = entry.getValue();

            totalRevenue += (long)rank.getWinningMoney() * count;
        }

        return (double)totalRevenue / purchaseAmount * 100.0;
    }

    private Map<Rank, Integer> initializeStatistics() {
        Map<Rank, Integer> statistics = new EnumMap<>(Rank.class);

        for (Rank rank : Rank.values()) {
            statistics.put(rank, 0);
        }

        return statistics;
    }
}
