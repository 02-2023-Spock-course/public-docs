package spock.course.lesson5app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity(name = "study_group")
public class StudyGroup extends BaseEntity implements Serializable {

    @Column(name = "name", length = 255, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "studyGroup")
    private Set<Student> students;
}
