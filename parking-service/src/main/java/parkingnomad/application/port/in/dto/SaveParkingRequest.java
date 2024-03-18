package parkingnomad.application.port.in.dto;

public record SaveParkingRequest(Long memberId, double latitude, double longitude) {
}
