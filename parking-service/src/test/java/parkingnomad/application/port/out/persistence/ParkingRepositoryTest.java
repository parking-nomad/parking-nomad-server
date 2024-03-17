package parkingnomad.application.port.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import parkingnomad.domain.Parking;
import parkingnomad.support.BaseTestWithContainers;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ParkingRepositoryTest extends BaseTestWithContainers {

    @Autowired
    ParkingRepository parkingRepository;

    @Test
    @DisplayName("parking을 저장한다.")
    void save() {
        //given
        final long memberId = 1L;
        final int latitude = 20;
        final int longitude = 30;
        final String address = "address";
        final Parking parking = Parking.createWithoutId(memberId, latitude, longitude, address);

        //when
        final Parking savedParking = parkingRepository.save(parking);

        //then
        final Parking foundParking = parkingRepository.findById(savedParking.getId()).get();
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(foundParking.getId()).isEqualTo(savedParking.getId());
            softAssertions.assertThat(foundParking.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(foundParking.getLatitude()).isEqualTo(latitude);
            softAssertions.assertThat(foundParking.getLongitude()).isEqualTo(longitude);
            softAssertions.assertThat(foundParking.getAddress()).isEqualTo(address);
        });
    }
}