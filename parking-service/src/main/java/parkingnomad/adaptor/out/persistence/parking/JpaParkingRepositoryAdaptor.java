package parkingnomad.adaptor.out.persistence.parking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaParkingRepositoryAdaptor extends JpaRepository<JpaParkingEntity, Long> {
    Optional<JpaParkingEntity> findJpaParkingEntityByIdAndMemberId(final Long id, final Long memberId);
}
