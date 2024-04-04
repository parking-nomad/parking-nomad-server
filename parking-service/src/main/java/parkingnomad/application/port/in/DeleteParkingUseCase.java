package parkingnomad.application.port.in;

public interface DeleteParkingUseCase {
    void deleteParking(final Long memberId, final Long parkingId);
}
