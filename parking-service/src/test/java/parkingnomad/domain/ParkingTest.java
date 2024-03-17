package parkingnomad.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ParkingTest {

    @Test
    @DisplayName("id가 없는 Parkikng을 생성한다.")
    void createParkingWithoutId() {
        //given
        long memberId = 1L;
        int latitude = 20;
        int longitude = 30;
        String address = "address";

        //when
        Parking parking = Parking.createWithoutId(memberId, latitude, longitude, address);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(parking.getId()).isNull();
            softAssertions.assertThat(parking.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(parking.getAddress()).isEqualTo(address);
            softAssertions.assertThat(parking.getLatitude()).isEqualTo(latitude);
            softAssertions.assertThat(parking.getLongitude()).isEqualTo(longitude);
        });
    }

    @Test
    @DisplayName("id를 포함한 Parking을 생성한다.")
    void createParkingWithId() {
        //given
        long id = 1L;
        long memberId = 2L;
        int latitude = 20;
        int longitude = 30;
        String address = "address";

        //when
        Parking parking = Parking.createWithId(id, memberId, latitude, longitude, address);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(parking.getId()).isEqualTo(id);
            softAssertions.assertThat(parking.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(parking.getAddress()).isEqualTo(address);
            softAssertions.assertThat(parking.getLatitude()).isEqualTo(latitude);
            softAssertions.assertThat(parking.getLongitude()).isEqualTo(longitude);
        });
    }
}