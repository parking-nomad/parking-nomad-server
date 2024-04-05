package parkingnomad.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkingnomad.vo.coordinate.Coordinates;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class FavoriteAreaTest {

    @Test
    @DisplayName("favoriteArea를 생성한다.")
    void createFavoriteArea() {
        //given
        final String nameValue = "회사";
        final Long memberId = 1L;
        final double latitude = 20;
        final double longitude = 30;
        final double latitudeDelta = 40;
        final double longitudeDelta = 50;
        final List<Coordinates> coordinates = List.of(
                Coordinates.from(-20, 80),
                Coordinates.from(-20, -20),
                Coordinates.from(60, -20),
                Coordinates.from(60, 80)
        );

        //when
        final FavoriteArea favoriteArea = FavoriteArea.createWithoutId(nameValue, memberId, latitude, longitude, latitudeDelta, longitudeDelta);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(favoriteArea.getId()).isNull();
            softAssertions.assertThat(favoriteArea.getMemberId()).isEqualTo(memberId);
            softAssertions.assertThat(favoriteArea.getNameValue()).isEqualTo(nameValue);
            softAssertions.assertThat(favoriteArea.getCreatedAt()).isNull();
            softAssertions.assertThat(favoriteArea.getUpdatedAt()).isNull();
            softAssertions.assertThat(favoriteArea.getAreaCoordinates())
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyElementsOf(coordinates);
        });
    }

}
