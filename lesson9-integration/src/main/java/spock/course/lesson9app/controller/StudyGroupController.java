package spock.course.lesson9app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spock.course.lesson9app.domain.dto.StudyGroupDto;
import spock.course.lesson9app.service.StudyGroupService;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping(path = "/study-group/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudyGroupDto> getStudyGroup(@PathVariable String name) {
        return ResponseEntity.ok(studyGroupService.getStudyGroupDto(name));
    }

    @PostMapping(path = "/study-group", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addStudyGroup(@RequestBody StudyGroupDto studyGroupDto) throws ExecutionException, InterruptedException {
        studyGroupService.addStudyGroup(studyGroupDto);
        return ResponseEntity.ok().build();
    }
}
