package spock.course.lesson8app.service


import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.test.context.ContextConfiguration
import spock.course.lesson8app.domain.CloudDisk
import spock.course.lesson8app.exception.CloudDiskException
import spock.lang.Retry
import spock.lang.Specification
import spock.lang.Stepwise
import spock.util.concurrent.BlockingVariable
import spock.util.concurrent.PollingConditions

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor

@Stepwise
@ContextConfiguration(classes = CloudDiskService)
class CloudDiskServiceTest extends Specification {

    static final DISK_NUM = 3
    static final GB_SIZE = 22

    @Autowired
    CloudDiskService service

    @SpringBean
    DataCenterService dataCenterService = Mock(DataCenterService)

    def "CreateCloudDisk"() {
        when:
        def disk = service.createCloudDisk(DISK_NUM, GB_SIZE)

        then:
        1 * dataCenterService.reserveDisk(_)
        disk
        with(disk) {
            name == "disk_$DISK_NUM"
            gbSize == GB_SIZE
            isReserved
        }
        noExceptionThrown()
    }

    def "SupplierCreatorCloudDisk"() {
        when:
        def supplier = service.supplierCreatorCloudDisk(DISK_NUM, GB_SIZE)
        def disk = supplier.get()

        then:
        supplier
        disk
        with(disk) {
            name == "disk_$DISK_NUM"
            gbSize == GB_SIZE
            isReserved
        }
        noExceptionThrown()
    }

    def "SupplierCreatorCloudDisk with InterruptedException"() {
        when:
        def supplier = service.supplierCreatorCloudDisk(DISK_NUM, GB_SIZE)
        def diskFromSupplier = supplier.get()

        then:
        1 * dataCenterService.reserveDisk(_) >> { throw new CloudDiskException(new Throwable()) }
        supplier
        thrown(CloudDiskException)
    }

    def "SupplierCreatorCloudDisk via CompletableFuture"() {
        given:
        def supplier = service.supplierCreatorCloudDisk(DISK_NUM, GB_SIZE)

        when:
        def future = CompletableFuture.supplyAsync(supplier)
        def disk = future.get()

        then:
        1 * dataCenterService.reserveDisk(_)
        supplier
        future.isDone()
        !future.isCompletedExceptionally()
        !future.isCancelled()
        disk
        noExceptionThrown()
    }

    def "SupplierCreatorCloudDisk via CompletableFuture with InterruptedException"() {
        when:
        def supplier = service.supplierCreatorCloudDisk(DISK_NUM, GB_SIZE)
        def future = CompletableFuture.supplyAsync(supplier)
        future.get()

        then:
        1 * dataCenterService.reserveDisk(_) >> { throw new CloudDiskException(new Throwable()) }
        supplier
        future.isDone()
        future.isCompletedExceptionally()
        thrown(ExecutionException)
    }

    @Retry(count = 5)
    def "SupplierCreatorCloudDisk via PollingConditions"() {
        given:
        1 * dataCenterService.reserveDisk(_) >> { Thread.sleep(500) }
        PollingConditions conditions = new PollingConditions(timeout: 10, initialDelay: 0, factor: 1.25)
        def supplier = service.supplierCreatorCloudDisk(DISK_NUM, GB_SIZE)

        when:
        def future = CompletableFuture.supplyAsync(supplier, testExecutor())

        then:
        conditions.within(0) {
            !future.isDone()
        }
        conditions.eventually {
            future.isDone()
            !future.isCompletedExceptionally()
        }
        def disk = future.get()
        with(disk) {
            isReserved
        }
        noExceptionThrown()
    }

    @Retry(count = 5)
    def "SupplierCreatorCloudDisk via BlockingVariable"() {
        given:
        def supplier = service.supplierCreatorCloudDisk(DISK_NUM, GB_SIZE)
        BlockingVariable blockVar = new BlockingVariable<CloudDisk>(2)
        1 * dataCenterService.reserveDisk(_) >> {
            blockVar.set(it)
        }

        when:
        CompletableFuture.supplyAsync(supplier, testExecutor())

        then:
        supplier
        def disk = blockVar.get()
        disk
        with(disk) {
            isReserved
        }
        noExceptionThrown()
    }

    @Retry(count = 5)
    def "AsyncCreateMultipleDisk"() {
        when:
        def count = 5
        def futureList = service.asyncCreateMultipleDisk(count, GB_SIZE, testExecutor())
        def allCf = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
        allCf.get()

        then:
        futureList.size() == count
        futureList.every { future ->
            future.isDone()
            !future.isCompletedExceptionally()
            interaction {
                def disk = future.get()
                with(disk) { cloudDisk ->
                    cloudDisk.name
                    cloudDisk.gbSize == GB_SIZE
                    cloudDisk.isReserved
                }
            }
        }
        noExceptionThrown()
        5 * dataCenterService.reserveDisk(_)
    }

    @Retry(count = 5)
    void "BlockingVariable simple example"() {
        given:
        def waitCondition = new BlockingVariable(2000)
        def runner = new Runnable() {
            @Override
            void run() {
                Thread.sleep(1000)
                waitCondition.set(true)
            }
        }

        when:
        new Thread(runner).run()

        then:
        true == waitCondition.get()
    }


    Executor testExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
        executor.setCorePoolSize(2)
        executor.setMaxPoolSize(2)
        executor.setQueueCapacity(100)
        executor.setThreadNamePrefix("test-task-")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }
}
