package spock.course.lesson9app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spock.course.lesson9app.domain.CloudDisk;
import spock.course.lesson9app.exception.CloudDiskException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudDiskService {

    private final DataCenterService dataCenterService;

    public CloudDisk createCloudDisk(int diskNum, int gbSize) throws InterruptedException {
        var name = generateName(diskNum);
        log.info("Start of creation CloudDisk name={}", name);
        var disk = new CloudDisk();
        disk.setName(name);
        disk.setGbSize(gbSize);
        dataCenterService.reserveDisk(disk);
        disk.setReserved(true);
        log.info("Successfully reserved {}", disk);
        return disk;
    }

    public Supplier<CloudDisk> supplierCreatorCloudDisk(int diskNum, int gbSize) throws InterruptedException {
        return new Supplier<>() {
            @Override
            public CloudDisk get() {
                try {
                    return createCloudDisk(diskNum, gbSize);
                } catch (InterruptedException e) {
                    throw new CloudDiskException(e);
                }
            }
        };
    }

    public List<CompletableFuture<CloudDisk>> asyncCreateMultipleDisk(final int count, final int gbSize
            , Executor executor) throws InterruptedException {

        var vmFutureList = new ArrayList<CompletableFuture<CloudDisk>>();
        var diskReserved = 0;
        while (++diskReserved <= count) {
            log.info("Start of creating new CloudDisk with number {} and GbSize {}", diskReserved, gbSize);
            var vmFuture =
                    CompletableFuture.supplyAsync(supplierCreatorCloudDisk(diskReserved, gbSize), executor)
                            .whenComplete((vm, ex) -> {
                                if (ex != null) {
                                    log.error("Failed creation Disk: ", ex);
                                } else {
                                    log.info("Successfully created Disk: {}", vm);
                                }
                            });
            vmFutureList.add(vmFuture);
        }
        return vmFutureList;
    }

    private String generateName(int vmNum) {
        return "disk_" + vmNum;
    }
}
