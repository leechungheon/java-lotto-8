package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputViewTest {

    private InputView inputView;

    @BeforeEach
    void setUp() {
        inputView = new InputView();
    }

    @AfterEach
    void restoreSystemIn() {
        Console.close();
    }

    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    // ====================================================================
    // 1. inputPurchaseAmount() 테스트
    // ====================================================================

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void 구입_금액이_숫자가_아니면_예외가_발생한다() {
        setInput("천원");
        assertThatThrownBy(() -> inputView.inputPurchaseAmount())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자를 입력해주세요.");
    }

    @DisplayName("구입 금액이 0원 이하이면 예외가 발생한다 (유효성 검증).")
    @Test
    void 구입_금액이_0원_이하이면_예외가_발생한다() {
        setInput("0");
        assertThatThrownBy(() -> inputView.inputPurchaseAmount())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 0원 이하의 금액은 입력할 수 없습니다.");
    }

    @DisplayName("구입 금액이 1000원 단위가 아니면 예외가 발생한다 (유효성 검증).")
    @Test
    void 구입_금액이_1000원_단위가_아니면_예외가_발생한다() {
        setInput("1500");
        assertThatThrownBy(() -> inputView.inputPurchaseAmount())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 금액을 1,000원 단위로 입력해주세요.");
    }

    @DisplayName("유효한 구입 금액은 정상적으로 반환된다.")
    @Test
    void 유효한_구입_금액은_정상적으로_반환된다() {
        setInput("3000");
        assertThat(inputView.inputPurchaseAmount()).isEqualTo(3000);
    }


    // ====================================================================
    // 2. inputWinningNumbers() 테스트
    // ====================================================================

    @DisplayName("당첨 번호에 숫자가 아닌 값이 포함되면 예외가 발생한다 (파싱).")
    @Test
    void 당첨_번호에_숫자가_아닌_값이_포함되면_예외가_발생한다() {
        setInput("1,2,a,4,5,6");
        assertThatThrownBy(() -> inputView.inputWinningNumbers())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 숫자로만 입력해야 합니다.");
    }

    @DisplayName("당첨 번호 입력에 쉼표(,)만 있으면 예외가 발생한다 (파싱).")
    @Test
    void 당첨_번호_입력에_쉼표만_있으면_예외가_발생한다() {
        setInput(",");
        assertThatThrownBy(() -> inputView.inputWinningNumbers())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효한 당첨 번호는 정상적으로 정수 리스트로 반환된다.")
    @Test
    void 유효한_당첨_번호는_정상적으로_정수_리스트로_반환된다() {
        setInput("1,10,20,30,40,45");
        List<Integer> expected = List.of(1, 10, 20, 30, 40, 45);
        assertThat(inputView.inputWinningNumbers()).isEqualTo(expected);
    }

    // ====================================================================
    // 3. inputBonusNumber() 테스트
    // ====================================================================

    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void 보너스_번호가_숫자가_아니면_예외가_발생한다() {
        setInput("보너스");
        assertThatThrownBy(() -> inputView.inputBonusNumber())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자를 입력해주세요.");
    }

    @DisplayName("보너스 번호가 범위를 초과하면 예외가 발생한다 (유효성 검증).")
    @Test
    void 보너스_번호가_범위를_초과하면_예외가_발생한다() {
        setInput("46");
        assertThatThrownBy(() -> inputView.inputBonusNumber())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 1이상 45이하의 숫자가 되어야 합니다.");
    }

    @DisplayName("유효한 보너스 번호는 정상적으로 반환된다.")
    @Test
    void 유효한_보너스_번호는_정상적으로_반환된다() {
        setInput("15");
        assertThat(inputView.inputBonusNumber()).isEqualTo(15);
    }
}