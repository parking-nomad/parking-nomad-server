package parkingnomad.application.port.in;

import parkingnomad.application.port.in.dto.ParkingResponse;

public interface FindLatestParkingByMemberIdUseCase {
    ParkingResponse findLatestParkingByMemberId(final Long memberId);
}
