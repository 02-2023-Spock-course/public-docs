package spock.course.lesson5app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spock.course.lesson5app.domain.StudyGroup;
import spock.course.lesson5app.service.StudyGroupService;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping(path = "/study-group/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyGroup getStudyGroup(@PathVariable String name) {
        return studyGroupService.findStudyGroup(name);
    }
}
