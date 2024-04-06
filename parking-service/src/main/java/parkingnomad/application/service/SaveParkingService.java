package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import parkingnomad.application.port.in.SaveParkingUseCase;
import parkingnomad.application.port.in.dto.SaveParkingRequest;
import parkingnomad.application.port.out.AddressLocator;
import parkingnomad.application.port.out.ImageUploader;
import parkingnomad.application.port.out.event.ParkingCreateEvent;
import parkingnomad.application.port.out.event.ParkingCreateEventPublisher;
import parkingnomad.application.port.out.memberloader.MemberLoader;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.domain.Parking;
import parkingnomad.exception.InvalidMemberIdException;
import parkingnomad.exception.LocationSearchException;

import static parkingnomad.exception.ParkingErrorCode.CANT_FIND_ADDRESS;
import static parkingnomad.exception.ParkingErrorCode.INVALID_MEMBER_ID;

@Service
@Transactional
public class SaveParkingService implements SaveParkingUseCase {

    private final MemberLoader memberLoader;
    private final ParkingRepository parkingRepository;
    private final AddressLocator addressLocator;
    private final ParkingCreateEventPublisher publisher;
    private final ImageUploader imageUploader;

    public SaveParkingService(
            final MemberLoader memberLoader,
            final ParkingRepository parkingRepository,
            final AddressLocator addressLocator,
            final ParkingCreateEventPublisher publisher,
            final ImageUploader imageUploader
    ) {
        this.memberLoader = memberLoader;
        this.parkingRepository = parkingRepository;
        this.addressLocator = addressLocator;
        this.publisher = publisher;
        this.imageUploader = imageUploader;
    }

    @Override
    public Long saveParking(final Long memberId, final SaveParkingRequest saveParkingRequest, final MultipartFile parkingImage) {
        validateMemberId(memberId);
        final double latitude = saveParkingRequest.latitude();
        final double longitude = saveParkingRequest.longitude();
        final String address = getAddressOrThrow(latitude, longitude);
        final Parking parking = Parking.createWithoutId(memberId, latitude, longitude, address);
        final String imageName = imageUploader.upload(parkingImage);
        parking.addImageName(imageName);
        final Long savedId = parkingRepository.save(parking).getId();
        publisher.publish(new ParkingCreateEvent(savedId, memberId));
        return savedId;
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
