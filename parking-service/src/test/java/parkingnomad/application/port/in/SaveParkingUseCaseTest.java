package parkingnomad.application.port.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import parkingnomad.MockParkingImageFile;
import parkingnomad.application.port.in.dto.SaveParkingRequest;
import parkingnomad.application.port.out.AddressLocator;
import parkingnomad.application.port.out.ImageUploader;
import parkingnomad.application.port.out.memberloader.MemberLoader;
import parkingnomad.application.port.out.persistence.ParkingRepository;
import parkingnomad.domain.Parking;
import parkingnomad.exception.InvalidMemberIdException;
import parkingnomad.exception.LocationSearchException;
import parkingnomad.support.BaseTestWithContainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SaveParkingUseCaseTest extends BaseTestWithContainers {

    final MultipartFile parkingImage = MockParkingImageFile.generate();

    @Autowired
    SaveParkingUseCase saveParkingUseCase;

    @Autowired
    ParkingRepository parkingRepository;

    @MockBean
    AddressLocator addressLocator;

    @MockBean
    MemberLoader memberLoader;

    @MockBean
    ImageUploader imageUploader;

    @Test
    @DisplayName("parking을 저장한다.")
    void saveParking() {
        //given
        final String address = "address";
        final long memberId = 1L;
        final int latitude = 20;
        final int longitude = 30;
        final String parkingImageName = "parkingImage";
        when(addressLocator.convertToAddress(anyDouble(), anyDouble())).thenReturn(Optional.of(address));
        when(memberLoader.isExistedMember(anyLong())).thenReturn(true);
        when(imageUploader.upload(any(MultipartFile.class))).thenReturn(parkingImageName);

        //when
        final Long savedId = saveParkingUseCase.saveParking(memberId, new SaveParkingRequest(latitude, longitude), parkingImage);

        //then
        final Parking parking = parkingRepository.findById(savedId).get();
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(parking.getId()).isEqualTo(savedId);
            softAssertions.assertThat(parking.getAddress()).isEqualTo(address);
            softAssertions.assertThat(parking.getImage()).isEqualTo(parkingImageName);
            softAssertions.assertThat(parking.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(parking.getLatitude()).isEqualTo(latitude);
            softAssertions.assertThat(parking.getLongitude()).isEqualTo(longitude);
        });
    }

    @Test
    @DisplayName("memberId가 유효하지 않은 경우 예외가 발생한다.")
    void saveParkingWithInvalidMemberId() {
        //given
        final String address = "address";
        final long memberId = 1L;
        final int latitude = 20;
        final int longitude = 30;
        when(addressLocator.convertToAddress(anyDouble(), anyDouble())).thenReturn(Optional.of(address));
        when(memberLoader.isExistedMember(anyLong())).thenReturn(false);
        final SaveParkingRequest saveParkingRequest = new SaveParkingRequest(latitude, longitude);

        //when & then
        assertThatThrownBy(() -> saveParkingUseCase.saveParking(memberId, saveParkingRequest, parkingImage))
                .isInstanceOf(InvalidMemberIdException.class)
                .hasMessageContaining("member_id가 유효하지 않습니다.");
    }

    @Test
    @DisplayName("좌표에 해당하는 주소를 찾지 못하는 경우 예외가 발생한다.")
    void saveParkingWithInvalidCoordinate() {
        //given
        when(addressLocator.convertToAddress(anyDouble(), anyDouble())).thenReturn(Optional.empty());
        when(memberLoader.isExistedMember(anyLong())).thenReturn(true);

        final long memberId = 1L;
        final double latitude = 40.748817;
        final double longitude = -73.985428;
        final SaveParkingRequest saveParkingRequest = new SaveParkingRequest(latitude, longitude);

        //when & then
        assertThatThrownBy(() -> saveParkingUseCase.saveParking(memberId, saveParkingRequest, parkingImage))
                .isInstanceOf(LocationSearchException.class)
                .hasMessageContaining("좌표에 해당하는 주소를 찾을 수 없습니다.");
    }
}
