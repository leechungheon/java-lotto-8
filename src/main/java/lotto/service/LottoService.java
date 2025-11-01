package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
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
}
