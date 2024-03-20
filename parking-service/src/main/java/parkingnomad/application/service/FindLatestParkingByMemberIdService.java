package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.FindLatestParkingByMemberId;
import parkingnomad.application.port.in.dto.ParkingResponse;
import parkingnomad.application.port.out.persistence.LatestParkingRepository;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.domain.Parking;
import parkingnomad.exception.NonExistentLatestParkingException;

import static parkingnomad.exception.ParkingErrorCode.NON_EXISTENT_LATEST_PARKING;

@Service
@Transactional
public class FindLatestParkingByMemberIdService implements FindLatestParkingByMemberId {

    private final LatestParkingRepository latestParkingRepository;
    private final ParkingRepository parkingRepository;

    public FindLatestParkingByMemberIdService(
            final LatestParkingRepository latestParkingRepository,
            final ParkingRepository parkingRepository
    ) {
        this.latestParkingRepository = latestParkingRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public ParkingResponse findLatestParkingByMemberId(final Long memberId) {
        final Parking parking = latestParkingRepository.findLatestParkingByMemberId(memberId)
                .orElseGet(() -> getParkingFromParkingRepository(memberId));
        return ParkingResponse.from(parking);
    }

    private Parking getParkingFromParkingRepository(final Long memberId) {
        final Parking parking = findLatestParkingByMemberIdOrThrow(memberId);
        latestParkingRepository.saveLatestParking(parking);
        return parking;
    }

    private Parking findLatestParkingByMemberIdOrThrow(final Long memberId) {
        return parkingRepository.findLatestParkingByMemberId(memberId)
                .orElseThrow(() -> new NonExistentLatestParkingException(NON_EXISTENT_LATEST_PARKING, memberId));
    }
}
