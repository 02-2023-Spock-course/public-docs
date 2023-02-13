package spock.course.lesson5app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import spock.course.lesson5app.domain.StudyGroup;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    Optional<StudyGroup> findFirstByName(@Param("name") String name);
}