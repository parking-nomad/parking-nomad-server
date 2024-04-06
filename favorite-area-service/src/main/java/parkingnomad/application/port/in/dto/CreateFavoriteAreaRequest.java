package parkingnomad.application.port.in.dto;

import parkingnomad.domain.FavoriteArea;

public record CreateFavoriteAreaRequest(
        String name,
        double latitude,
        double longitude,
        double latitudeDelta,
        double longitudeDelta
) {

    public FavoriteArea toDomain(final Long memberId) {
        return FavoriteArea.createWithoutId(name, memberId, latitude, longitude, latitudeDelta, longitudeDelta);
    }
}
