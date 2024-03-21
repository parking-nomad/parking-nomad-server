package parkingnomad.application.port.out.persistence;

import parkingnomad.domain.Parking;

import java.util.Optional;

public interface LatestParkingRepository {

    void saveLatestParking(final Parking parking);

    Optional<Parking> findLatestParkingByMemberId(final Long memberId);

    void deleteLatestParkingByMemberId(final Long memberId);
}
