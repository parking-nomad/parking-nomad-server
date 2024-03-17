package parkingnomad.adaptor.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaParkingRepositoryAdaptor extends JpaRepository<JpaParkingEntity, Long> {
}
