package spock.course.lesson7app.service


import spock.course.lesson7app.enums.StageType
import spock.lang.Specification

class Example8MultipleMockReturnTest extends Specification {

    AuditService auditServiceStub = Stub()
    AuditService auditServiceMock = Mock()

    def "example 1"() {
        given:
        auditServiceStub.sendToAudit(_, _) >>> [true, false]
        auditServiceMock.sendToAudit(_, _) >>> [true, false]

        when:
        def firstStub = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def secondStub = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def firstMock = auditServiceMock.sendToAudit(StageType.BEFORE_ACTION, null)
        def secondMock = auditServiceMock.sendToAudit(StageType.BEFORE_ACTION, null)

        then:
        firstStub
        !secondStub
        firstMock
        !secondMock
        noExceptionThrown()
    }

    def "example 2"() {
        given:
        auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, _) >> false
        auditServiceStub.sendToAudit(StageType.AFTER_ACTION, _) >> true

        when:
        def after = auditServiceStub.sendToAudit(StageType.AFTER_ACTION, null)
        def before = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)

        then:
        !before
        after
        noExceptionThrown()
    }

}
