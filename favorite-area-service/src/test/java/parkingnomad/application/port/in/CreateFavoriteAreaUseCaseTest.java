package parkingnomad.application.port.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import parkingnomad.application.port.in.dto.CreateFavoriteAreaRequest;
import parkingnomad.application.port.out.memberloader.MemberLoader;
import parkingnomad.application.port.out.persistence.FavoriteAreaRepository;
import parkingnomad.domain.FavoriteArea;
import parkingnomad.exception.NonExistentMemberException;
import parkingnomad.support.BaseTestWithContainers;
import parkingnomad.vo.coordinate.Coordinates;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CreateFavoriteAreaUseCaseTest extends BaseTestWithContainers {

    @Autowired
    FavoriteAreaRepository repository;

    @Autowired
    CreateFavoriteAreaUseCase useCase;

    @MockBean
    MemberLoader memberLoader;

    @Test
    @DisplayName("FavoriteArea를 저장한다.")
    void createFavoriteArea() {
        //given
        when(memberLoader.isExistedMember(anyLong())).thenReturn(true);
        final Long memberId = 1L;
        final String name = "집";
        final double latitude = 20;
        final double longitude = 30;
        final double latitudeDelta = 40;
        final double longitudeDelta = 50;
        final CreateFavoriteAreaRequest request = new CreateFavoriteAreaRequest(name, latitude, longitude, latitudeDelta, longitudeDelta);
        final List<Coordinates> expectedArea = List.of(
                Coordinates.from(-20, 80),
                Coordinates.from(-20, -20),
                Coordinates.from(60, -20),
                Coordinates.from(60, 80)
        );

        //when
        final Long savedId = useCase.createFavoriteArea(memberId, request);

        //then
        final FavoriteArea found = repository.findById(savedId).get();
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(found.getId()).isEqualTo(savedId);
            softAssertions.assertThat(found.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(found.getNameValue()).isEqualTo(name);
            softAssertions.assertThat(found.getAreaCoordinates())
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyElementsOf(expectedArea);
        });
    }

    @Test
    @DisplayName("memberId가 유효하지 않은 경우 예외가 발생한다.")
    void createFavoriteAreaFailByMemberId() {
        //given
        when(memberLoader.isExistedMember(anyLong())).thenReturn(false);
        final Long memberId = 1L;
        final String name = "집";
        final double latitude = 20;
        final double longitude = 30;
        final double latitudeDelta = 40;
        final double longitudeDelta = 50;
        final CreateFavoriteAreaRequest request = new CreateFavoriteAreaRequest(name, latitude, longitude, latitudeDelta, longitudeDelta);

        //when & then
        assertThatThrownBy(() -> useCase.createFavoriteArea(memberId, request))
                .isInstanceOf(NonExistentMemberException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }
}
