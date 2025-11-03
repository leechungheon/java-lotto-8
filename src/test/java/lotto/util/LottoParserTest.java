package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoParserTest {

    @DisplayName("유효한 문자열은 정수형 리스트로 정상 변환된다.")
    @Test
    void parseWinningNumbers_ValidInput() {
        String input = "1,10,20,30,40,45";
        List<Integer> expected = List.of(1, 10, 20, 30, 40, 45);

        List<Integer> result = LottoParser.parseWinningNumbers(input);

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("파싱 시 공백과 후행 쉼표가 있어도 정상 변환된다.")
    @Test
    void parseWinningNumbers_WithSpacesAndCommas() {
        String input = " 1, 10 ,20, , 30,40 , 45 ";
        List<Integer> expected = List.of(1, 10, 20, 30, 40, 45);

        List<Integer> result = LottoParser.parseWinningNumbers(input);

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("숫자가 아닌 문자가 포함되면 NumberFormatException 대신 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,a,3,4,5,6", "1.5,2,3,4,5,6", "1,2,3,four,5,6"})
    void parseWinningNumbers_ContainsNonNumericCharacters(String invalidInput) {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 숫자로만 입력해야 합니다.");
    }

    @DisplayName("번호 개수가 6개가 아니면 예외가 발생한다 (유효성 검증).")
    @Test
    void parseWinningNumbers_InvalidSize() {
        String input = "1,2,3,4,5"; // 5개 입력

        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 6개여야 합니다.");
    }

    @DisplayName("번호에 중복된 숫자가 있으면 예외가 발생한다 (유효성 검증).")
    @Test
    void parseWinningNumbers_Duplication() {
        String input = "1,2,3,4,5,5";

        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 서로 중복될 수 없습니다.");
    }

    @DisplayName("번호에 1-45 범위를 벗어나는 숫자가 있으면 예외가 발생한다 (유효성 검증).")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,46", "1,2,3,4,5,0"})
    void parseWinningNumbers_InvalidRange(String input) {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 1이상 45이하의 숫자가 되어야 합니다.");
    }
}