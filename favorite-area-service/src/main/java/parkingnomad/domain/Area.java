package parkingnomad.domain;

import parkingnomad.vo.coordinate.Coordinates;

import java.util.List;

public class Area {
    private final List<Coordinates> coordinates;

    private Area(final List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public static Area from(final double latitude, final double longitude, final double latitudeDelta, final double longitudeDelTa) {
        final double minLatitude = latitude - latitudeDelta;
        final double maxLatitude = latitude + latitudeDelta;

        final double minLongitude = longitude - longitudeDelTa;
        final double maxLongitude = longitude + longitudeDelTa;

        final Coordinates firstVertex = Coordinates.from(minLatitude, maxLongitude);
        final Coordinates secondVertex = Coordinates.from(minLatitude, minLongitude);
        final Coordinates thirdVertex = Coordinates.from(maxLatitude, minLongitude);
        final Coordinates forthVertex = Coordinates.from(maxLatitude, maxLongitude);

        return new Area(List.of(firstVertex, secondVertex, thirdVertex, forthVertex));
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }
}
