package parkingnomad.application.port.in;

public interface DeleteLatestParkingByMemberIdAndParkingUseCase {
    void deleteLatestParkingByMemberIdAndParking(final Long memberId, final Long parkingId);
}
