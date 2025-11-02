package lotto.view;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.dto.LottoDto;
import lotto.dto.LottosResponse;
import lotto.model.Rank;

public class OutputView {
    public void outputRequestPurchaseAmount(){
        System.out.println("구입금액을 입력해 주세요.");
    }
    private void outputPurchaseCount(int count){
        System.out.println("\n"+count+"개를 구매했습니다.");
    }

    public void outputPurchasedLottos(LottosResponse responseDto) {
        outputPurchaseCount(responseDto.getLottoCount());

        for (LottoDto lottoDto : responseDto.getLottos()) {
            System.out.println(lottoDto.getNumbers());
        }
    }

    public void outputRequestWinningNumbers(){
        System.out.println("\n당첨 번호를 입력해 주세요.");
    }

    public void outputRequestBonusNumber(){
        System.out.println("\n보너스 번호를 입력해 주세요.");
    }

    public void outputWinningStatistics(Map<Rank, Integer> statistics, double profitRate) {
        System.out.println("당첨 통계");
        System.out.println("---");

        Map<Rank, Integer> sortedStatistics = sortRanks(statistics);

        for (Map.Entry<Rank, Integer> entry : sortedStatistics.entrySet()) {
            Rank rank = entry.getKey();

            if (rank == Rank.MISS) {
                continue;
            }

            System.out.println(formatRankOutput(rank, entry.getValue()));
        }

        outputProfitRate(profitRate);
    }

    private void outputProfitRate(double profitRate) {
        String formattedRate = String.format("%,.1f", profitRate);

        System.out.println("총 수익률은 " + formattedRate + "%입니다.");
    }

    private Map<Rank, Integer> sortRanks(Map<Rank, Integer> statistics) {
        return statistics.entrySet().stream()
                .filter(entry -> entry.getKey().getWinningMoney() > 0)
                .sorted(Comparator.comparingInt(e -> e.getKey().getMatchCount()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private String formatRankOutput(Rank rank, int count) {
        String matchText = rank.getMatchCount() + "개 일치";

        if (rank == Rank.SECOND) {
            matchText += ", 보너스 볼 일치";
        }

        String money = String.format("%,d", rank.getWinningMoney());

        return String.format("%s (%s원) - %d개", matchText, money, count);
    }
}
