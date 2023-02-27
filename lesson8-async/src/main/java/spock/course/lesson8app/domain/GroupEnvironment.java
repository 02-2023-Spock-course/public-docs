package spock.course.lesson8app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GroupEnvironment implements Serializable {

    private List<VirtualMachine> virtualMachines;

    private List<CloudDisk> cloudDisks;
}
