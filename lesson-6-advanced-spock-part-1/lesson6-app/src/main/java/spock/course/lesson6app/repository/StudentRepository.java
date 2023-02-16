package spock.course.lesson6app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spock.course.lesson6app.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
