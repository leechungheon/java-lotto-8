package lotto.model;

import java.util.Arrays;

public enum Rank {
    FIRST(6, false, 2_000_000_000),
    SECOND(5, true, 30_000_000),
    THIRD(5, false, 1_500_000),
    FOURTH(4, false, 50_000),
    FIFTH(3, false, 5_000),
    MISS(0, false, 0);

    private final int matchCount;
    private final boolean matchBonus;
    private final long winningMoney;

    Rank(int matchCount, boolean matchBonus, long winningMoney) {
        this.matchCount = matchCount;
        this.matchBonus = matchBonus;
        this.winningMoney = winningMoney;
    }


    public long getWinningMoney() {
        return winningMoney;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isMatchBonus() {
        return matchBonus;
    }

    public static Rank valueOf(int matchCount, boolean matchBonus) {
        if (matchCount < FIFTH.matchCount) {
            return MISS;
        }

        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount)
                .filter(rank -> {
                    if (rank.matchCount != 5) {
                        return true;
                    }
                    return rank.matchBonus == matchBonus;
                })
                .filter(rank -> rank != MISS)
                .findFirst()
                .orElse(MISS);
    }
}
