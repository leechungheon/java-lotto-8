package lotto.dto;

import java.util.List;

public class LottosResponse {
    private final int lottoCount;
    private final List<LottoDto> lottos;

    public LottosResponse(int lottoCount, List<LottoDto> lottos) {
        this.lottoCount = lottoCount;
        this.lottos = lottos;
    }

    public int getLottoCount() {
        return lottoCount;
    }

    public List<LottoDto> getLottos() {
        return lottos;
    }
}