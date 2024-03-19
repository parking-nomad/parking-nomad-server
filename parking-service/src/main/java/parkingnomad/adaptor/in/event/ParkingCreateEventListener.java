package parkingnomad.adaptor.in.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import parkingnomad.application.port.in.SaveLatestParkingUseCase;
import parkingnomad.application.port.out.event.ParkingCrateEvent;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
public class ParkingCreateEventListener {

    private final SaveLatestParkingUseCase saveLatestParkingUseCase;

    public ParkingCreateEventListener(final SaveLatestParkingUseCase saveLatestParkingUseCase) {
        this.saveLatestParkingUseCase = saveLatestParkingUseCase;
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleParkingCreateEvent(final ParkingCrateEvent parkingCrateEvent) {
        saveLatestParkingUseCase.saveLatestParking(parkingCrateEvent.id());
    }
}
