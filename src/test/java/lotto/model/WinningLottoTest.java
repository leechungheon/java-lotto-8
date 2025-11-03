package lotto.model;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    private static class StubLotto extends Lotto {
        private final List<Integer> stubNumbers;

        public StubLotto(List<Integer> numbers) {
            super(numbers);
            this.stubNumbers = numbers;
        }

        @Override
        public List<Integer> getNumbers() {
            return stubNumbers;
        }
    }

    private static final List<Integer> BASE_WINNING_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    private static final int BASE_BONUS_NUMBER = 7;

    private List<Integer> parseNumbers(String numbers) {
        return Stream.of(numbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    @DisplayName("생성 시 보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 6})
    void constructor_ShouldThrowExceptionWhenBonusDuplicatesWinningNumber(int duplicateNumber) {
        assertThatThrownBy(() -> new WinningLotto(BASE_WINNING_NUMBERS, duplicateNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호가 당첨 번호와 중복될 수 없습니다.");
    }

    @DisplayName("유효한 번호로 생성 시 예외가 발생하지 않는다.")
    @Test
    void constructor_ShouldPassWithValidNumbers() {
        assertThatCode(() -> new WinningLotto(BASE_WINNING_NUMBERS, BASE_BONUS_NUMBER))
                .doesNotThrowAnyException();
    }

    @DisplayName("구매 로또와 당첨 번호의 일치 개수를 정확히 센다.")
    @ParameterizedTest(name = "일치 개수 {1}개")
    @CsvSource(value = {
            "1,2,3,4,5,6 | 6",
            "1,2,3,4,5,7 | 5",
            "1,2,3,4,7,8 | 4",
            "1,2,3,7,8,9 | 3",
            "1,2,7,8,9,10 | 2",
            "10,11,12,13,14,15 | 0"
    }, delimiter = '|')
    void countMatch_ShouldReturnCorrectCount(String purchasedNumbersString, int expectedCount) {
        WinningLotto winningLotto = new WinningLotto(BASE_WINNING_NUMBERS, 7);

        List<Integer> purchasedNumbers = parseNumbers(purchasedNumbersString);
        Lotto purchasedLotto = new StubLotto(purchasedNumbers);

        int actualCount = winningLotto.countMatch(purchasedLotto);

        assertThat(actualCount).isEqualTo(expectedCount);
    }


    @DisplayName("구매 로또에 보너스 번호가 있으면 true를 반환한다.")
    @Test
    void containsBonus_ShouldReturnTrueWhenBonusIsPresent() {
        WinningLotto winningLotto = new WinningLotto(BASE_WINNING_NUMBERS, BASE_BONUS_NUMBER);

        Lotto purchasedLotto = new StubLotto(List.of(1, 2, 3, 4, 5, BASE_BONUS_NUMBER));

        assertThat(winningLotto.containsBonus(purchasedLotto)).isTrue();
    }

    @DisplayName("구매 로또에 보너스 번호가 없으면 false를 반환한다.")
    @Test
    void containsBonus_ShouldReturnFalseWhenBonusIsNotPresent() {
        WinningLotto winningLotto = new WinningLotto(BASE_WINNING_NUMBERS, BASE_BONUS_NUMBER);

        Lotto purchasedLotto = new StubLotto(List.of(1, 2, 3, 4, 5, 8));

        assertThat(winningLotto.containsBonus(purchasedLotto)).isFalse();
    }
}