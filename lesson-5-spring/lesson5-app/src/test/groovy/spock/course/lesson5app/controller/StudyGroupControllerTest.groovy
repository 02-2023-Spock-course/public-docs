package spock.course.lesson5app.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.course.lesson5app.domain.StudyGroup
import spock.course.lesson5app.service.StudyGroupService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class StudyGroupControllerTest extends Specification {

    static final NAME = 'name1'
    static final STUDY_GROUP = new StudyGroup(name: NAME)

    @Autowired
    MockMvc mvc

    @Autowired
    StudyGroupService studyGroupService

    def "getStudyGroup"() {
        given:
        studyGroupService.findStudyGroup(NAME) >> new StudyGroup(name: NAME)

        expect: "controller is available"
        def resultAction = mvc.perform(MockMvcRequestBuilders.get("/study-group/$NAME"))
        resultAction.andExpect(status().isOk())
        resultAction.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        resultAction.andExpect(content().string('{"id":null,"name":"name1","students":null}'))
    }

    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        StudyGroupService studyGroupService() {
            return detachedMockFactory.Stub(StudyGroupService)
        }
    }
}
