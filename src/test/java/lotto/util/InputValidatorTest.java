package lotto.util;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    // ====================================================================
    // 1. validateInputPurchaseAmount() 테스트
    // ====================================================================

    @DisplayName("구입 금액이 0원 이하일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1000, -5000})
    void validateInputPurchaseAmount_ZeroOrNegative(int invalidAmount) {
        assertThatThrownBy(() -> InputValidator.validateInputPurchaseAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 0원 이하의 금액은 입력할 수 없습니다.");
    }

    @DisplayName("구입 금액이 1,000원 단위가 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1500, 2010, 999})
    void validateInputPurchaseAmount_NotUnitOf1000(int invalidAmount) {
        assertThatThrownBy(() -> InputValidator.validateInputPurchaseAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 금액을 1,000원 단위로 입력해주세요.");
    }

    @DisplayName("유효한 구입 금액은 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1000, 3000, 10000})
    void validateInputPurchaseAmount_Valid(int validAmount) {
        assertThatCode(() -> InputValidator.validateInputPurchaseAmount(validAmount))
                .doesNotThrowAnyException();
    }

    // ====================================================================
    // 2. validateInputWinningNumbers() 테스트
    // ====================================================================

    @DisplayName("당첨 번호 개수가 6개가 아니면 예외가 발생한다.")
    @Test
    void validateInputWinningNumbers_InvalidSize() {
        // 5개 입력
        List<Integer> invalidSizeNumbers = List.of(1, 2, 3, 4, 5);

        assertThatThrownBy(() -> InputValidator.validateInputWinningNumbers(invalidSizeNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 6개여야 합니다.");
    }

    private static Stream<List<Integer>> invalidRangeNumbersProvider() {
        return Stream.of(
                List.of(1, 2, 3, 4, 5, 0),    // 0이 포함되어 범위 오류
                List.of(1, 2, 3, 4, 5, 46),   // 46이 포함되어 범위 오류
                List.of(1, 2, 3, 40, -1, 45) // 음수가 포함되어 범위 오류
        );
    }

    @DisplayName("당첨 번호에 1~45 범위를 벗어나는 숫자가 있으면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidRangeNumbersProvider")
    void validateInputWinningNumbers_InvalidRange(List<Integer> invalidRangeNumbers) {
        assertThatThrownBy(() -> InputValidator.validateInputWinningNumbers(invalidRangeNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 1이상 45이하의 숫자가 되어야 합니다.");
    }

    @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void validateInputWinningNumbers_Duplication() {
        List<Integer> duplicatedNumbers = List.of(1, 2, 3, 4, 5, 5);

        assertThatThrownBy(() -> InputValidator.validateInputWinningNumbers(duplicatedNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 서로 중복될 수 없습니다.");
    }

    @DisplayName("유효한 당첨 번호는 예외가 발생하지 않는다.")
    @Test
    void validateInputWinningNumbers_Valid() {
        List<Integer> validNumbers = List.of(1, 10, 20, 30, 40, 45);

        assertThatCode(() -> InputValidator.validateInputWinningNumbers(validNumbers))
                .doesNotThrowAnyException();
    }

    // ====================================================================
    // 3. validateBonusNumberRange() 테스트
    // ====================================================================

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 46, -1})
    void validateBonusNumberRange_InvalidRange(int invalidNumber) {
        assertThatThrownBy(() -> InputValidator.validateBonusNumberRange(invalidNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 1이상 45이하의 숫자가 되어야 합니다.");
    }

    @DisplayName("유효한 보너스 번호는 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 25, 45})
    void validateBonusNumberRange_Valid(int validNumber) {
        assertThatCode(() -> InputValidator.validateBonusNumberRange(validNumber))
                .doesNotThrowAnyException();
    }

    private List<Integer> parseNumbers(String numbers) {
        return List.of(numbers.split(","))
                .stream()
                .map(Integer::parseInt)
                .toList();
    }
}