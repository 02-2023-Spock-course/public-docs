package spock.course.lesson7app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractMessage<T> implements Serializable {

    private String header;

    private String description;

    private LocalDateTime sentDateTime;

    private List<T> data;
}
