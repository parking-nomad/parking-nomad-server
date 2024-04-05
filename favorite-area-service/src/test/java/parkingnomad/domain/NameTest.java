package parkingnomad.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import parkingnomad.exception.InvalidNameException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static parkingnomad.domain.Name.from;

class NameTest {

    @Test
    @DisplayName("Area name을 생성한다.")
    void createName() {
        //given
        final String value = "집";

        //when
        final Name name = from(value);

        //then
        assertThat(name.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @DisplayName("Area name이 1글자 이상 20글자 이하가 아닌 경우 예외가 발생한다.")
    @NullAndEmptySource
    @ValueSource(strings = {"", "  ", "스무글자가넘는이름을가진유효하지않은이름1"})
    void createNameFailByInvalidValue(final String invalidValue) {
        //when & then
        assertThatThrownBy(() -> from(invalidValue))
                .isInstanceOf(InvalidNameException.class)
                .hasMessageContaining("즐겨찾는 구역의 이름은 1글자 이상 20글자 이하여야 합니다.");
    }

}
