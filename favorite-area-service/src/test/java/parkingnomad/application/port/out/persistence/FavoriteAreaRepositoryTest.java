package parkingnomad.application.port.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import parkingnomad.domain.FavoriteArea;
import parkingnomad.support.BaseTestWithContainers;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class FavoriteAreaRepositoryTest extends BaseTestWithContainers {

    @Autowired
    FavoriteAreaRepository favoriteAreaRepository;

    @Test
    @DisplayName("FavoriteArea를 저장한다.")
    void save() {
        //given
        final String name = "집";
        final long memberId = 1L;
        final int latitude = 20;
        final int longitude = 30;
        final int latitudeDelta = 40;
        final int longitudeDelta = 50;
        final FavoriteArea favoriteArea = FavoriteArea.createWithoutId(name, memberId, latitude, longitude, latitudeDelta, longitudeDelta);

        //when
        final FavoriteArea saved = favoriteAreaRepository.saveFavoriteArea(favoriteArea);

        //then
        final FavoriteArea found = favoriteAreaRepository.findById(saved.getId()).get();
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(found.getId()).isNotNull();
            softAssertions.assertThat(found.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(found.getNameValue()).isEqualTo(name);
            softAssertions.assertThat(found.getAreaCoordinates()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(favoriteArea.getAreaCoordinates());
            softAssertions.assertThat(found.getCreatedAt()).isNotNull();
            softAssertions.assertThat(found.getUpdatedAt()).isNotNull();
        });
    }
}
