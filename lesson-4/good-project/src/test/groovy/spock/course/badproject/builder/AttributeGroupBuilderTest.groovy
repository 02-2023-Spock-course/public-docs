package spock.course.badproject.builder

import examples.ExampleDtoGenerator
import spock.course.badproject.dto.in.AttributeDto
import spock.course.badproject.service.AuditService
import spock.lang.Specification
import spock.lang.Subject

class AttributeGroupBuilderTest extends Specification {

    AuditService auditService = Stub() {
        sendAttributeToAudit(_) >> true
    }
    def attributeBuilder = new AttributeBuilder(auditService)

    def "build with empty list"() {
        given:
        @Subject def attributeGroupBuilder = new AttributeGroupBuilder(attributeBuilder)

        when:
        def attributeGroups = attributeGroupBuilder.build([])

        then:
        attributeGroups.size() == 0
        noExceptionThrown()
    }

    def "build with one input attribute"() {
        given:
        def inAttribute = ExampleDtoGenerator.buildAttributeDto()
        @Subject def attributeGroupBuilder = new AttributeGroupBuilder(attributeBuilder)

        when:
        def attributeGroups = attributeGroupBuilder.build([inAttribute])

        then:
        attributeGroups.size() == 1
        with(attributeGroups[0]) {
            name == inAttribute.groupName
            attributes.size() == 1
        }
        noExceptionThrown()
    }

    def "build with multiple input attribute"() {
        given:
        def dtoAttributes = [
                new AttributeDto(key: "KEY1", value: "VALUE1", type: "TYPE", groupName: "GROUP1"),
                new AttributeDto(key: "KEY2", value: "VALUE2", type: "TYPE", groupName: "GROUP2"),
                new AttributeDto(key: "KEY3", value: "VALUE3", type: "TYPE", groupName: "GROUP1"),
                new AttributeDto(key: "KEY4", value: "VALUE4", type: "TYPE", groupName: "GROUP3")]
        def attributeGroupBuilder = new AttributeGroupBuilder(attributeBuilder)
        def expectedGroupList = ["GROUP1", "GROUP2", "GROUP3"]
        def keysList = ["KEY1", "KEY2", "KEY3", "KEY4"]

        when:
        def attributeGroups = attributeGroupBuilder.build(dtoAttributes)

        then:
        attributeGroups.size() == 3
        attributeGroups*.name == expectedGroupList
        attributeGroups.name == expectedGroupList

        //it's not Assertions - выполняется всегда без ошибок
        for (String groupName : attributeGroups.name) {
            !dtoAttributes.groupName in groupName
            dtoAttributes.groupName in groupName
        }

        //it's not Assertions - выполняется всегда без ошибок
        for (def key : attributeGroups.attributes.key) {
            key
            !key
            key == null
            key != key
        }

        //it's not Assertions - выполняется всегда без ошибок
        attributeGroups.each {
            it.name
            !it.name
            it.name == null
            it.name != it.name
            it.name !== it.name
        }

        //it's not Assertions - выполняется всегда без ошибок
        for (final def name in attributeGroups) {
            name === "1111111111111111111111"
        }

        attributeGroups.every { it.attributes.every { it.isReadOnly == false } }

        attributeGroups.every {
            it.name ==~ /GROUP[0-9]/
        }

        attributeGroups.any() {
            it.name
        }
    }
}