package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.FindParkingByIdUseCase;
import parkingnomad.application.port.in.dto.ParkingResponse;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.domain.Parking;
import parkingnomad.exception.NonExistentParkingException;

import static parkingnomad.exception.ParkingErrorCode.NON_EXISTENT_PARKING;

@Service
@Transactional(readOnly = true)
public class FindParkingByIdService implements FindParkingByIdUseCase {

    private final ParkingRepository parkingRepository;

    public FindParkingByIdService(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public ParkingResponse findParkingById(final Long id) {
        final Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new NonExistentParkingException(NON_EXISTENT_PARKING,id));
        return ParkingResponse.from(parking);
    }
}
