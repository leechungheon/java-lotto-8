package lotto.model;

import java.util.Collections;
import java.util.List;

public class Lottos {

    private final List<Lotto> lottos;

    public Lottos(List<Lotto> lottos) {
        this.lottos = lottos;
    }

    public int size() {
        return lottos.size();
    }

    /**
     * 외부에서 로또 리스트를 조회하기 위한 Getter.
     * 불변 리스트를 반환하여 내부 데이터를 보호합니다.
     * @return 변경 불가능한 Lotto 리스트
     */
    public List<Lotto> getLottos() {
        return Collections.unmodifiableList(lottos);
    }
}