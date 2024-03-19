package parkingnomad.application.port.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import parkingnomad.domain.Parking;
import parkingnomad.support.BaseTestWithContainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class LatestParkingRepositoryTest extends BaseTestWithContainers {

    @Autowired
    LatestParkingRepository latestParkingRepository;

    @Test
    @DisplayName("최근 주차정보를 저장한다.")
    void save() {
        //given
        final Long id = 1L;
        final Long memberId = 2L;
        final int latitude = 20;
        final int longitude = 30;
        final String address = "address";
        final LocalDateTime now = LocalDateTime.now();
        final Parking parking = Parking.createWithId(id, memberId, latitude, longitude, address, now, now);

        //when
        latestParkingRepository.saveLatestParking(parking);

        //then
        final Parking found = latestParkingRepository.findLatestParkingByMemberId(2L).get();
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(found.getId()).isEqualTo(id);
            softAssertions.assertThat(found.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(found.getLatitude()).isEqualTo(latitude);
            softAssertions.assertThat(found.getLongitude()).isEqualTo(longitude);
            softAssertions.assertThat(found.getAddress()).isEqualTo(address);
            softAssertions.assertThat(found.getCreatedAt()).isEqualTo(now);
            softAssertions.assertThat(found.getUpdatedAt()).isEqualTo(now);
        });
    }

    @Test
    @DisplayName("memberId가 일치하는 최근 주차 정보가 없는 경우 Optional.empty를 반환한다.")
    void findLatestParkingFail() {
        //when
        final Optional<Parking> latestParkingByMemberId = latestParkingRepository.findLatestParkingByMemberId(2L);

        //then
        assertThat(latestParkingByMemberId).isEmpty();
    }
}