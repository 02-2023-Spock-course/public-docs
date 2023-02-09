package spock.course.badproject.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttributeDto {

    private String key;

    private String value;

    private String type;

    private String groupName;

}
