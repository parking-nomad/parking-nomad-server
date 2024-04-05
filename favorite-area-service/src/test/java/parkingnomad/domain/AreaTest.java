package parkingnomad.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkingnomad.vo.coordinate.Coordinates;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class AreaTest {

    @Test
    @DisplayName("Area를 생성한다.")
    void createArea() {
        //given
        final double latitude = 20;
        final double longitude = 30;
        final double latitudeDelta = 10;
        final double longitudeDelta = 40;

        final List<Coordinates> coordinates = List.of(
                Coordinates.from(10, 70),
                Coordinates.from(10, -10),
                Coordinates.from(30, -10),
                Coordinates.from(30, 70)
        );

        //when
        final Area area = Area.of(latitude, longitude, latitudeDelta, longitudeDelta);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(area.getCoordinates()).hasSize(4);
            softAssertions.assertThat(area.getCoordinates()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(coordinates);
        });
    }
}
