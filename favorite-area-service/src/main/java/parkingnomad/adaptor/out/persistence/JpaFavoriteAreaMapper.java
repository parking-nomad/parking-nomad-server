package parkingnomad.adaptor.out.persistence;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;
import parkingnomad.domain.Area;
import parkingnomad.domain.FavoriteArea;
import parkingnomad.vo.coordinate.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JpaFavoriteAreaMapper {

    public static final int SRID = 4326;
    private final static GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    public FavoriteArea toDomainEntity(final JpaFavoriteAreaEntity jpaFavoriteArea) {
        final Area area = createAreaFromPolygon(jpaFavoriteArea.getArea());
        return FavoriteArea.createWithId(
                jpaFavoriteArea.getId(),
                jpaFavoriteArea.getMemberId(),
                jpaFavoriteArea.getName(),
                area,
                jpaFavoriteArea.getCreatedAt(),
                jpaFavoriteArea.getUpdatedAt()
        );
    }

    private Area createAreaFromPolygon(final Polygon polygon) {
        final List<Coordinates> coordinates = Arrays.stream(polygon.getCoordinates())
                .map(coordinate -> Coordinates.from(coordinate.getY(), coordinate.getX()))
                .collect(Collectors.toList());
        coordinates.remove(coordinates.size() - 1);
        return new Area(List.copyOf(coordinates));
    }

    public JpaFavoriteAreaEntity toJpaEntity(final FavoriteArea favoriteArea) {
        final Polygon polygon = createPolygonFromArea(favoriteArea);
        return new JpaFavoriteAreaEntity(favoriteArea.getMemberId(), favoriteArea.getNameValue(), polygon);
    }

    private Polygon createPolygonFromArea(final FavoriteArea favoriteArea) {
        final List<Coordinates> areaCoordinates = favoriteArea.getAreaCoordinates();
        final List<Coordinate> jpaCoordinates = new ArrayList<>();
        Coordinate firstCoordinate = null;
        for (final Coordinates areaCoordinate : areaCoordinates) {
            final Coordinate coordinate = new Coordinate(areaCoordinate.getLongitude(), areaCoordinate.getLatitude());
            jpaCoordinates.add(coordinate);
            if (Objects.isNull(firstCoordinate)) {
                firstCoordinate = coordinate;
            }
        }
        jpaCoordinates.add(firstCoordinate);
        final Polygon polygon = GEOMETRY_FACTORY.createPolygon(jpaCoordinates.toArray(new Coordinate[0]));
        polygon.setSRID(SRID);
        return polygon;
    }
}
