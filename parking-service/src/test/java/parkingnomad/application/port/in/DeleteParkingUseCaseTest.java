package parkingnomad.application.port.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.domain.Parking;
import parkingnomad.support.BaseTestWithContainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteParkingUseCaseTest extends BaseTestWithContainers {

    @Autowired
    DeleteParkingUseCase useCase;

    @Autowired
    ParkingRepository repository;

    @Test
    @DisplayName("memberId와 parkingId를 통해 parking을 삭제한다.")
    void deleteParking() {
        //given
        final Parking saved = repository.save(Parking.createWithoutId(1L, 20, 30, "address"));

        //when
        useCase.deleteParking(saved.getMemberId(), saved.getId());

        //then
        final Optional<Parking> found = repository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
