package parkingnomad.application.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import parkingnomad.application.port.in.SaveParkingUseCase;
import parkingnomad.application.port.in.dto.SaveParkingRequest;
import parkingnomad.application.port.out.AddressLocator;
import parkingnomad.application.port.out.MemberLoader;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.domain.Parking;
import parkingnomad.exception.InvalidMemberIdException;
import parkingnomad.exception.LocationSearchException;

import static parkingnomad.exception.CoordinatesErrorCode.CANT_FIND_ADDRESS;
import static parkingnomad.exception.ParkingErrorCode.INVALID_MEMBER_ID;

@Service
@Transactional
public class SaveParkingService implements SaveParkingUseCase {

    private final MemberLoader memberLoader;
    private final ParkingRepository parkingRepository;
    private final AddressLocator addressLocator;

    public SaveParkingService(
            final MemberLoader memberLoader,
            final ParkingRepository parkingRepository,
            final AddressLocator addressLocator
    ) {
        this.memberLoader = memberLoader;
        this.parkingRepository = parkingRepository;
        this.addressLocator = addressLocator;
    }

    @Override
    public Long saveParking(final SaveParkingRequest saveParkingRequest) {
        final Long memberId = saveParkingRequest.memberId();
        validateMemberId(memberId);
        final double latitude = saveParkingRequest.latitude();
        final double longitude = saveParkingRequest.longitude();
        final String address = getAddressOrThrow(latitude, longitude);
        final Parking parking = Parking.createWithoutId(memberId, latitude, longitude, address);
        return parkingRepository.save(parking).getId();
    }

    private String getAddressOrThrow(final double latitude, final double longitude) {
        return addressLocator.convertToAddress(latitude, longitude)
                .orElseThrow(() -> new LocationSearchException(CANT_FIND_ADDRESS, latitude, longitude));
    }

    private void validateMemberId(final Long memberId) {
        final boolean isExistedMember = memberLoader.isExistedMember(memberId);
        if (!isExistedMember) {
            throw new InvalidMemberIdException(INVALID_MEMBER_ID, memberId);
        }
    }
}
