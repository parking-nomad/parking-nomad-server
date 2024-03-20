package parkingnomad.adaptor.in.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import parkingnomad.application.port.in.SaveLatestParkingUseCase;
import parkingnomad.application.port.out.event.ParkingCreateEvent;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
public class ParkingCreateEventListener {

    private final SaveLatestParkingUseCase saveLatestParkingUseCase;
    private final ParkingCreateEventBroker broker;


    public ParkingCreateEventListener(
            final SaveLatestParkingUseCase saveLatestParkingUseCase,
            final ParkingCreateEventBroker broker
    ) {
        this.saveLatestParkingUseCase = saveLatestParkingUseCase;
        this.broker = broker;
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleParkingCreateEvent(final ParkingCreateEvent parkingCreateEvent) {
        final Long memberId = parkingCreateEvent.memberId();
        final Long parkingId = parkingCreateEvent.parkingId();
        broker.consume(memberId, () -> saveLatestParkingUseCase.saveLatestParking(parkingId));
    }
}
