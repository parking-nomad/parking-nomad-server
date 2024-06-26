package parkingnomad.application.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.DeleteLatestParkingByMemberIdAndParkingUseCase;
import parkingnomad.application.port.out.persistence.LatestParkingRepository;
import parkingnomad.domain.Parking;

import java.util.function.Consumer;

@Component
@Transactional
public class DeleteLatestParkingByMemberIdAndParkingIdService implements DeleteLatestParkingByMemberIdAndParkingUseCase {

    private final LatestParkingRepository latestParkingRepository;

    public DeleteLatestParkingByMemberIdAndParkingIdService(final LatestParkingRepository latestParkingRepository) {
        this.latestParkingRepository = latestParkingRepository;
    }

    @Override
    public void deleteLatestParkingByMemberIdAndParking(final Long memberId, final Long parkingId) {
        latestParkingRepository.findLatestParkingByMemberId(memberId)
                .ifPresent(deleteLatestParking(memberId, parkingId));
    }

    private Consumer<Parking> deleteLatestParking(final Long memberId, final Long parkingId) {
        return latestParking -> {
            if (latestParking.isSameId(parkingId)) {
                latestParkingRepository.deleteLatestParkingByMemberId(memberId);
            }
        };
    }
}
