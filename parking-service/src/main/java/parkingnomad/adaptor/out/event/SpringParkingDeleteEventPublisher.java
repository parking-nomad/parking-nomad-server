package parkingnomad.adaptor.out.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import parkingnomad.application.port.out.event.ParkingDeleteEvent;
import parkingnomad.application.port.out.event.ParkingDeleteEventPublisher;

@Component
public class SpringParkingDeleteEventPublisher implements ParkingDeleteEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringParkingDeleteEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(final ParkingDeleteEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
