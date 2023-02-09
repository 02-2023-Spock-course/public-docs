package spock.course.badproject.builder

import examples.ExampleDtoGenerator
import spock.course.badproject.exception.AuditException
import spock.course.badproject.service.AuditService
import spock.lang.Specification

class AttributeBuilderTest extends Specification {

    AuditService auditService = Stub(AuditService)
    AttributeBuilder attributeBuilder

    def setup() {
        attributeBuilder = new AttributeBuilder(auditService)
        auditService.sendAttributeToAudit(_) >> true
    }

    def "build multiple attributes"() {
        given:
        def attributeDto = ExampleDtoGenerator.buildAttributeDto()

        when:
        def attributes = attributeBuilder.buildListWithFilter([attributeDto, attributeDto], attributeDto.groupName)

        then:
        attributes.size() == 2
        noExceptionThrown()
    }

    def "build one attribute"() {
        given:
        def attributeDto = ExampleDtoGenerator.buildAttributeDto()

        when:
        def attribute = attributeBuilder.build(attributeDto)

        then:
        attribute
        with(attribute) {
            key == attributeDto.key
            value == attributeDto.value
            type == attributeDto.type
        }
        noExceptionThrown()
    }

    def "build one attribute and failed in AuditSystem"() {
        given:
        AuditService failedAuditService = Stub {
            sendAttributeToAudit(_) >> false
        }
        attributeBuilder = new AttributeBuilder(failedAuditService)
        def attributeDto = ExampleDtoGenerator.buildAttributeDto()

        when:
        attributeBuilder.build(attributeDto)

        then:
        thrown(AuditException)
    }
}
