package parkingnomad.application.port.in;

public interface DeleteLatestParkingByMemberIdUseCase {
    void deleteLatestParkingByMemberId(final Long memberId);
}
