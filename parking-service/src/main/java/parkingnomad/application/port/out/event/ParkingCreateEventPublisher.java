package parkingnomad.application.port.out.event;

public interface ParkingCreateEventPublisher {
    void publish(final ParkingCreateEvent event);
}
