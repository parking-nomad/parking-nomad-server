package parkingnomad.application.port.in;

import parkingnomad.application.port.in.dto.ParkingResponse;

public interface FindParkingByIdUseCase {
    ParkingResponse findParkingById(final Long id);
}
