package spock.course.lesson6app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "student",
        uniqueConstraints = {
        @UniqueConstraint(name = "uc_student_name_surname", columnNames = { "name", "surname" })
})
public class Student extends BaseEntity implements Serializable {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "surname", length = 100, nullable = false)
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;
}
