package parkingnomad.application.port.out.event;

public record ParkingCreateEvent(Long parkingId, Long memberId) {
}
