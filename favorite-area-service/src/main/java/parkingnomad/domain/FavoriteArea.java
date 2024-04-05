package parkingnomad.domain;

import parkingnomad.vo.coordinate.Coordinates;

import java.time.LocalDateTime;
import java.util.List;

public class FavoriteArea {
    private Long id;
    private Long memberId;
    private Name name;
    private Area area;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FavoriteArea(
            final Long id,
            final Long memberId,
            final Name name,
            final Area area,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.area = area;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FavoriteArea createWithoutId(
            final String nameValue,
            final Long memberId,
            final double latitude,
            final double longitude,
            final double latitudeDelta,
            final double longitudeDelta
    ) {
        final Name name = Name.from(nameValue);
        final Area area = Area.from(latitude, longitude, latitudeDelta, longitudeDelta);
        return new FavoriteArea(null, memberId, name, area, null, null);
    }

    public static FavoriteArea createWithId(
            final Long id,
            final Long memberId,
            final String nameValue,
            final double latitude,
            final double longitude,
            final double latitudeDelta,
            final double longitudeDelta,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        final Name name = Name.from(nameValue);
        final Area area = Area.from(latitude, longitude, latitudeDelta, longitudeDelta);
        return new FavoriteArea(id, memberId, name, area, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public List<Coordinates> getAreaCoordinates() {
        return area.getCoordinates();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
