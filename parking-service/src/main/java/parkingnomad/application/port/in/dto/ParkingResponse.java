package parkingnomad.application.port.in.dto;

import parkingnomad.domain.Parking;

import java.time.LocalDateTime;

public record ParkingResponse(
        Long id,
        Long memberId,
        double latitude,
        double longitude,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ParkingResponse from(final Parking parking) {
        return new ParkingResponse(
                parking.getId(),
                parking.getMemberId(),
                parking.getLatitude(),
                parking.getLongitude(),
                parking.getAddress(),
                parking.getCreatedAt(),
                parking.getUpdatedAt()
        );
    }
}
