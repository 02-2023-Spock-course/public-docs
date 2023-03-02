package spock.course.lesson9app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VirtualMachine {

    private String name;

    private int CpuSize;

    private int RamSize;

    private boolean isReserved;

}
