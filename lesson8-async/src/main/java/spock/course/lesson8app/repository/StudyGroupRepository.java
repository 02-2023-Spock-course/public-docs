package spock.course.lesson8app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import spock.course.lesson8app.domain.StudyGroupEntity;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {

    Optional<StudyGroupEntity> findFirstByName(@Param("name") String name);
}