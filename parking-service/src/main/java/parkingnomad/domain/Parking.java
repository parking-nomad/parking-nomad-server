package parkingnomad.domain;

import java.time.LocalDateTime;

public class Parking {
    private final Long id;
    private final Long memberId;
    private final Coordinates coordinates;
    private final String address;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Parking(
            final Long id,
            final Long memberId,
            final Coordinates coordinates,
            final String address,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.coordinates = coordinates;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Parking createWithoutId(
            final Long memberId,
            final double latitude,
            final double longitude,
            final String address
    ) {
        return new Parking(null, memberId, Coordinates.from(latitude, longitude), address, null, null);
    }

    public static Parking createWithId(
            final Long id,
            final Long memberId,
            final double latitude,
            final double longitude,
            final String address,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        return new Parking(id, memberId, Coordinates.from(latitude, longitude), address, createdAt, updatedAt);
    }

    public double getLatitude() {
        return coordinates.getLatitude();
    }

    public double getLongitude() {
        return coordinates.getLongitude();
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
