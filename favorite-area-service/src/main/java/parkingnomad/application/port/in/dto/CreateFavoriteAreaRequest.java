package parkingnomad.application.port.in.dto;

public record CreateFavoriteAreaRequest(
        String name,
        double latitude,
        double longitude,
        double latitudeDelta,
        double longitudeDelta
) {
}
