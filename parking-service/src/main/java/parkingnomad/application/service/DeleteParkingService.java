package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.DeleteParkingUseCase;
import parkingnomad.application.port.out.event.ParkingDeleteEvent;
import parkingnomad.application.port.out.event.ParkingDeleteEventPublisher;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.exception.NonExistentParkingException;
import parkingnomad.exception.ParkingErrorCode;

@Service
@Transactional
public class DeleteParkingService implements DeleteParkingUseCase {

    private final ParkingRepository parkingRepository;
    private final ParkingDeleteEventPublisher parkingDeleteEventPublisher;

    public DeleteParkingService(
            final ParkingRepository parkingRepository,
            final ParkingDeleteEventPublisher parkingDeleteEventPublisher
    ) {
        this.parkingRepository = parkingRepository;
        this.parkingDeleteEventPublisher = parkingDeleteEventPublisher;
    }

    @Override
    public void deleteParking(final Long memberId, final Long parkingId) {
        validate(memberId, parkingId);
        parkingRepository.deleteById(parkingId);
        parkingDeleteEventPublisher.publish(new ParkingDeleteEvent(memberId, parkingId));
    }

    private void validate(final Long memberId, final Long parkingId) {
        parkingRepository.findByIdAndMemberId(memberId, parkingId)
                .orElseThrow(() -> new NonExistentParkingException(ParkingErrorCode.NON_EXISTENT_PARKING, parkingId));
    }
}
