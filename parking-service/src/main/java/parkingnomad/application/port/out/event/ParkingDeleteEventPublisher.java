package parkingnomad.application.port.out.event;

public interface ParkingDeleteEventPublisher {

    void publish(final ParkingDeleteEvent event);
}
