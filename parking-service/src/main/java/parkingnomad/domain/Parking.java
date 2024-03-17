package parkingnomad.domain;

public class Parking {
    private final Long id;
    private final Long memberId;
    private final Coordinates coordinates;
    private final String address;

    private Parking(Long id, Long memberId, Coordinates coordinates, String address) {
        this.id = id;
        this.memberId = memberId;
        this.coordinates = coordinates;
        this.address = address;
    }

    public static Parking createWithoutId(
            final Long memberId,
            final double latitude,
            final double longitude,
            final String address
    ) {
        return new Parking(null, memberId, Coordinates.from(latitude, longitude), address);
    }

    public static Parking createWithId(
            final Long id,
            final Long memberId,
            final double latitude,
            final double longitude,
            final String address
    ) {
        return new Parking(id, memberId, Coordinates.from(latitude, longitude), address);
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
}
