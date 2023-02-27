package spock.course.lesson8app.service

import org.spockframework.spring.SpringBean
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import spock.course.lesson8app.domain.CloudDisk
import spock.course.lesson8app.domain.VirtualMachine
import spock.lang.Retry
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Supplier

class GroupEnvironmentServiceTest extends Specification {

    GroupEnvironmentService groupEnvironmentService

    @SpringBean
    VirtualMachineService virtualMachineService

    @SpringBean
    CloudDiskService cloudDiskService

    @SpringBean
    Executor mainExecutor = diskExecutor()

    @SpringBean
    Executor vmAsyncExecutor = vmExecutor()

    @Retry(count = 5)
    @Timeout(5)
    def "ReserveGroupEnvironment"() {
        given:
        virtualMachineService = Mock()
        cloudDiskService = Mock()
        groupEnvironmentService = new GroupEnvironmentService(virtualMachineService, cloudDiskService, mainExecutor, vmAsyncExecutor)
        def count = 1
        def cpu = 3
        def ram = 3
        def diskSize = 44
        def vmCfList = [CompletableFuture.supplyAsync(getVmSupplier(), vmAsyncExecutor)]
        def diskCfList = [CompletableFuture.supplyAsync(getDiskSupplier(), mainExecutor)]

        when:
        def cfVoid = groupEnvironmentService.reserveGroupEnvironment(count, cpu, ram, diskSize)

        then:
        1 * virtualMachineService.asyncCreateMultipleVM(count, cpu, ram, vmAsyncExecutor) >> vmCfList
        1 * cloudDiskService.asyncCreateMultipleDisk(count, diskSize, mainExecutor) >> diskCfList
        vmCfList.every { cf ->
            cf.isDone()
            !cf.isCompletedExceptionally()
        }
        diskCfList.every { cf ->
            cf.isDone()
            !cf.isCompletedExceptionally()
        }
        cfVoid.isDone()
        !cfVoid.isCompletedExceptionally()
    }

    Executor vmExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
        executor.setCorePoolSize(1)
        executor.setMaxPoolSize(1)
        executor.setQueueCapacity(5)
        executor.setThreadNamePrefix("test-task-")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }

    Executor diskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
        executor.setCorePoolSize(1)
        executor.setMaxPoolSize(1)
        executor.setQueueCapacity(5)
        executor.setThreadNamePrefix("test-task-")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }

    Supplier<VirtualMachine> getVmSupplier() {
        new Supplier<VirtualMachine>() {
            @Override
            VirtualMachine get() {
                new VirtualMachine(name: "vm")
            }
        }
    }

    Supplier<CloudDisk> getDiskSupplier() {
        new Supplier<CloudDisk>() {
            @Override
            CloudDisk get() {
                new CloudDisk(name: "disk")
            }
        }
    }
}
