package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import lotto.model.Lotto;

public class LottoService {
    public void purchaseLotto(){
        List<Integer> lottoNumbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        new Lotto(lottoNumbers);
    }
}
