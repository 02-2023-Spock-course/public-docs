package spock.course.lesson9app.integration

import com.google.gson.Gson
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.matchers.Times
import org.mockserver.verify.VerificationTimes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.course.lesson9app.domain.dto.StudyGroupDto
import spock.course.lesson9app.enums.AuditEventResponseType
import spock.course.lesson9app.service.StudyGroupService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Tag
import spock.course.lesson9app.domain.dto.AuditEventResponseDto
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Tag("integration-test")
@SpringBootTest
@AutoConfigureMockMvc
class WebMvcIntegrationTest extends Specification {

    static final GROUP_NAME = "name1"
    static final STUDENT_COUNT = 5
    static final MOCK_SERVER_PORT = 8089
    static final MOCK_SERVER_HOST = "127.0.0.1"
    static final SRC_GROUP = new StudyGroupDto(name: GROUP_NAME, studentCount: STUDENT_COUNT)
    static final AUDIT_RESPONSE_DTO = new AuditEventResponseDto(auditEventResponseType: AuditEventResponseType.SUCCESS, message: "")

    @Autowired
    MockMvc mvc

    @Autowired
    StudyGroupService studyGroupService

    @Shared
    ClientAndServer mockServer

    @Shared
    def gson = new Gson()

    def setupSpec() {
        mockServer = ClientAndServer.startClientAndServer(MOCK_SERVER_PORT)
    }

    def cleanupSpec() {
        mockServer.stop()
    }

    @Timeout(10)
    def "http-post add new group"() {
        given:
        successResponseAuditWebServer()
        def json = gson.toJson(SRC_GROUP)

        when:
        def resultPostAction = mvc.perform(MockMvcRequestBuilders
                .post(URI.create("/study-group"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
        def resultGetAction = mvc.perform(MockMvcRequestBuilders
                .get(URI.create("/study-group/$GROUP_NAME"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))

        then:
        resultPostAction
        resultPostAction.andExpect(status().isOk())

        resultGetAction.andExpect(status().isOk())
        resultGetAction.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        resultGetAction.andExpect(content().json(json))

        mockServer.verify(request(), VerificationTimes.once())

        noExceptionThrown()
    }

    def successResponseAuditWebServer() {
        new MockServerClient(MOCK_SERVER_HOST, mockServer.getPort())
                .when(request()
                        .withMethod("POST")
                        .withPath("/audit/event")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE),
                        Times.once())
                .respond(response()
                        .withStatusCode(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8")
                        .withBody(gson.toJson(AUDIT_RESPONSE_DTO), org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withDelay(TimeUnit.SECONDS, 1)
                )
    }
}
