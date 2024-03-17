package parkingnomad.application.port.out;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import parkingnomad.support.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

class AddressLocatorTest extends BaseTest {

    @Autowired
    AddressLocator addressLocator;

    @Test
    @DisplayName("좌표를 도로명 주소로 변환한다.")
    void convertToAddress() {
        //given
        String address = "경기도 안성시 죽산면 죽산초교길 69-4";
        double latitude = 37.0789561558879;
        double longitude = 127.423084873712;

        //when
        String result = addressLocator.convertToAddress(latitude, longitude);

        //then
        assertThat(result).isEqualTo(address);
    }
}