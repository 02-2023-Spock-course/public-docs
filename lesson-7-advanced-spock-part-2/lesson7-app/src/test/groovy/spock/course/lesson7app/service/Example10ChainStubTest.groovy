package spock.course.lesson7app.service

import spock.course.lesson7app.enums.StageType
import spock.lang.Specification

class Example10ChainStubTest extends Specification {

    AuditService auditServiceStub = Stub()

    def "example test"() {
        given:
        auditServiceStub.sendToAudit(_, _) >>> [true,false,true] >> { throw new RuntimeException("error")} >> false

        when:
        def callStub1 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def callStub2 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def callStub3 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def callStub4 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def callStub5 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def callStub6 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)
        def callStub7 = auditServiceStub.sendToAudit(StageType.BEFORE_ACTION, null)

        then:
        callStub1
        callStub1 == callStub3
        !callStub2
        !callStub4
        !callStub5
        callStub5 == callStub6
        callStub6 == callStub7
        thrown(RuntimeException)
    }
}
