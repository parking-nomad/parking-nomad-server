package parkingnomad.adaptor.in.event;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class ParkingCreateEventBroker {
    private final Map<Long, BlockingQueue<Runnable>> tasksByMemberId = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void consume(final Long memberId, final Runnable savingLatestParking) {
        final BlockingQueue<Runnable> tasks = tasksByMemberId.get(memberId);
        if (isNull(tasks)) {
            final BlockingQueue<Runnable> newTasks = new LinkedBlockingQueue<>();
            tasksByMemberId.put(memberId, newTasks);
            newTasks.offer(savingLatestParking);
            process(memberId, newTasks);
            return;
        }
        tasks.offer(savingLatestParking);
    }

    private void process(final Long memberId, final BlockingQueue<Runnable> tasks) {
        executorService.execute(() -> {
            try {
                executeAndClearTasksByMemberId(memberId, tasks);
            } catch (InterruptedException e) {
                removeTasksByMemberId(memberId);
                throw new IllegalStateException(e);
            }
        });
    }

    private void executeAndClearTasksByMemberId(final Long memberId, final BlockingQueue<Runnable> tasks) throws InterruptedException {
        Runnable savingLatestParking = tasks.poll(1, SECONDS);
        while (!isNull(savingLatestParking)) {
            savingLatestParking.run();
            savingLatestParking = tasks.poll(1, SECONDS);
        }
        removeTasksByMemberId(memberId);
    }

    private void removeTasksByMemberId(final Long memberId) {
        tasksByMemberId.remove(memberId);
    }
}
