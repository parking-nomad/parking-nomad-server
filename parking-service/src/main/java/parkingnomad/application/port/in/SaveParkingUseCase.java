package parkingnomad.application.port.in;

import parkingnomad.application.port.in.dto.SaveParkingRequest;

public interface SaveParkingUseCase {
    Long saveParking(final Long memberId, final SaveParkingRequest saveParkingRequest);
}
