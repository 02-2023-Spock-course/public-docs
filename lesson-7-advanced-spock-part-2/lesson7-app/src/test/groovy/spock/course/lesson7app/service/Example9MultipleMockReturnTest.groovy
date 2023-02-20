package spock.course.lesson7app.service

import spock.course.lesson7app.enums.StageType
import spock.lang.Specification

class Example9MultipleMockReturnTest extends Specification {

    AuditService auditServiceStub = Stub()
    AuditService auditServiceMock = Mock()

    def "example test"() {
        given:
        auditServiceStub.sendToAudit(_, _) >> { it[0] == StageType.BEFORE_ACTION ? true : false }
        auditServiceMock.sendToAudit(_, _) >> { it[0] == StageType.BEFORE_ACTION ? false : true }

        when:
        def beforeStub = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def afterStub = auditServiceStub.sendToAudit(StageType.AFTER_ACTION, null)
        def beforeMock = auditServiceMock.sendToAudit(StageType.BEFORE_ACTION, null)
        def afterMock = auditServiceMock.sendToAudit(StageType.AFTER_ACTION, null)

        then:
        beforeStub
        !afterStub
        !beforeMock
        afterMock
        noExceptionThrown()
    }
}
