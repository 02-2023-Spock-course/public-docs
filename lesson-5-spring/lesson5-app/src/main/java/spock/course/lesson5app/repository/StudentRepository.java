package spock.course.lesson5app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spock.course.lesson5app.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
