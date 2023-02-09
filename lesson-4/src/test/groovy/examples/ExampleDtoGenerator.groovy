package examples

import spock.course.badproject.dto.in.AttributeDto;

class ExampleDtoGenerator {

    static AttributeDto buildAttributeDto() {
        return new AttributeDto(key: "KEY", value: "VALUE", type: "TYPE", groupName: "GROUP_NAME")
    }

}
