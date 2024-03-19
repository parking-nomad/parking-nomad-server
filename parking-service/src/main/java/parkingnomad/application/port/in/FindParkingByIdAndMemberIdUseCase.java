package parkingnomad.application.port.in;

import parkingnomad.application.port.in.dto.ParkingResponse;

public interface FindParkingByIdAndMemberIdUseCase {
    ParkingResponse findParkingByIdAndMemberId(final Long id, final Long memberId);
}
