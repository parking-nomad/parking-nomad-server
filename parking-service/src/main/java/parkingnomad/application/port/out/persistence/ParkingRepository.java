package parkingnomad.application.port.out.persistence;

import parkingnomad.domain.Parking;

import java.util.Optional;

public interface ParkingRepository {
    Parking save(final Parking parking);

    Optional<Parking> findById(final Long id);

    Optional<Parking> findByIdAndMemberId(final Long id, final Long memberId);
}
