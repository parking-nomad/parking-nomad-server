package parkingnomad.application.port.out.event;

public record ParkingDeleteEvent(Long memberId, Long parkingId) {
}
