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

    // LottoService 내부의 당첨 통계를 계산하는 메서드 (예시)

    public Map<Rank, Integer> calculateWinningStatistics(WinningLotto winningLotto) {
        Map<Rank, Integer> statistics = initializeStatistics();

        // 구매한 모든 로또(Lotto)를 순회합니다.
        for (Lotto lotto : purchasedLottos.getLottos()) {

            // 1. 일치 개수 및 보너스 일치 여부를 계산
            int matchCount = winningLotto.countMatch(lotto);
            boolean matchBonus = winningLotto.containsBonus(lotto);

            // 2. Rank 모델의 valueOf 메서드를 사용하여 등수를 결정
            Rank rank = Rank.valueOf(matchCount, matchBonus);

            // 3. 통계에 누적
            statistics.put(rank, statistics.getOrDefault(rank, 0) + 1);
        }

        return statistics;
    }

    private Map<Rank, Integer> initializeStatistics() {
        // EnumMap은 Enum을 키로 사용할 때 가장 효율적이고 안전한 Map입니다.
        Map<Rank, Integer> statistics = new EnumMap<>(Rank.class);

        // 모든 Rank 상수에 대해 카운트를 0으로 초기화
        for (Rank rank : Rank.values()) {
            statistics.put(rank, 0);
        }

        return statistics;
    }
}
