package parkingnomad.application.port.out.event;

import org.springframework.stereotype.Component;

@Component
public interface ParkingCreateEventPublisher {
    void publish(final ParkingCreateEvent event);
}
