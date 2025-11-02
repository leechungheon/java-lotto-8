package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lotto.dto.LottoDto;
import lotto.dto.LottosResponse;
import lotto.model.Lotto;
import lotto.model.Lottos;

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

        // 1. List<Lotto>를 List<LottoDto>로 변환
        List<LottoDto> lottoDtos = purchasedLottos.getLottos().stream()
                .map(lotto -> new LottoDto(lotto.getNumbers()))
                .collect(Collectors.toList());

        // 2. 최종 응답 DTO 생성 및 반환
        return new LottosResponse(
                purchasedLottos.size(),
                lottoDtos
        );
    }
}
