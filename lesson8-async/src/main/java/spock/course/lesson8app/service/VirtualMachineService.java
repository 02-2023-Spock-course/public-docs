package spock.course.lesson8app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spock.course.lesson8app.domain.VirtualMachine;
import spock.course.lesson8app.exception.VirtualMachineException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class VirtualMachineService {

    private final DataCenterService dataCenterService;

    public VirtualMachine createVM(int vmNum, int cpuSize, int ramSize) throws InterruptedException {
        var name = generateName(vmNum);
        log.info("Start of creation VM name={}", name);
        var vm = new VirtualMachine();
        vm.setName(name);
        vm.setCpuSize(cpuSize);
        vm.setCpuSize(ramSize);
        dataCenterService.reserveVM(vm);
        vm.setReserved(true);
        return vm;
    }

    public Supplier<VirtualMachine> supplierCreatorVM(int vmNum, int cpuSize, int ramSize) throws InterruptedException {
        return new Supplier<>() {
            @Override
            public VirtualMachine get() {
                try {
                    return createVM(vmNum, cpuSize, ramSize);
                } catch (InterruptedException e) {
                    throw new VirtualMachineException(e);
                }
            }
        };
    }

    public List<CompletableFuture<VirtualMachine>> asyncCreateMultipleVM(final int count, final int cpuSize, final int ramSize
            , Executor executor) throws InterruptedException {

        var vmFutureList = new ArrayList<CompletableFuture<VirtualMachine>>();
        var vmReserved = 0;
        while (++vmReserved <= count) {
            log.info("Start of creating new VM with number {} with cpuSize {} and ramSize {}", vmReserved, cpuSize, ramSize);
            var vmFuture =
                    CompletableFuture.supplyAsync(supplierCreatorVM(vmReserved, cpuSize, ramSize), executor)
                            .whenComplete((vm, ex) -> {
                                if (ex != null) {
                                    log.error("Failed creation CM: ", ex);
                                } else {
                                    log.info("Successfully created VM: {}", vm);
                                }
                            });
            vmFutureList.add(vmFuture);
        }
        return vmFutureList;
    }

    private String generateName(int vmNum) {
        return "vm_" + vmNum;
    }
}
