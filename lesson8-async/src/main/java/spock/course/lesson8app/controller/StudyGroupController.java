package spock.course.lesson8app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spock.course.lesson8app.domain.StudyGroupDto;
import spock.course.lesson8app.domain.StudyGroupEntity;
import spock.course.lesson8app.service.StudyGroupService;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping(path = "/study-group/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyGroupEntity getStudyGroup(@PathVariable String name) {
        return studyGroupService.findStudyGroup(name);
    }

    @PostMapping(path = "/study-group", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addStudyGroup(@RequestBody StudyGroupDto studyGroupDto) throws ExecutionException, InterruptedException {
        studyGroupService.addStudyGroup(studyGroupDto);
        return ResponseEntity.ok().build();
    }
}
