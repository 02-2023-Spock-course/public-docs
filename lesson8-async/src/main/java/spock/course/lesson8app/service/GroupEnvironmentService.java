package spock.course.lesson8app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spock.course.lesson8app.exception.GroupEnvironmentException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
@RequiredArgsConstructor
public class GroupEnvironmentService {

    private final VirtualMachineService virtualMachineService;
    private final CloudDiskService cloudDiskService;
    private final Executor mainExecutor;
    private final Executor vmAsyncExecutor;

    @Async("mainExecutor")
    public CompletableFuture<Void> reserveGroupEnvironment(final int count, final int cpuSize, final int ramSize,
                                        final int diskGbSize) throws InterruptedException, ExecutionException {
        var start = System.currentTimeMillis();
        log.info("Start of creation Group environment");

        var vmFutureList =
                virtualMachineService.asyncCreateMultipleVM(count, cpuSize, ramSize, vmAsyncExecutor);

        var diskFutureList =
                cloudDiskService.asyncCreateMultipleDisk(count, diskGbSize, mainExecutor);

        var allFutureList = Stream
                .concat(vmFutureList.stream(), diskFutureList.stream())
                .toList();

        CompletableFuture<Void> allFutures =
                CompletableFuture.allOf(allFutureList.toArray(new CompletableFuture[0]));

        while(!allFutures.isDone()) {
            log.info("GroupEnvironment is in the process of creating...seconds {}", (System.currentTimeMillis() - start)/1000);
        }

        validate(vmFutureList);
        validate(allFutureList);

        log.info("Successfully created Group environment");
        return allFutures;
    }

    private void validate(List<? extends CompletableFuture<? extends Object>> cfList) {
        if(cfList.stream().anyMatch(CompletableFuture::isCompletedExceptionally)) {
            throw new GroupEnvironmentException("GroupEnvironment failed to create!");
        }
    }
}
