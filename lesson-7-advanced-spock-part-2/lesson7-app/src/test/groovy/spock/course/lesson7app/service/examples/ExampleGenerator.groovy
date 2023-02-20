package spock.course.lesson7app.service.examples

import spock.course.lesson7app.domain.Homework
import spock.course.lesson7app.domain.HomeworkMessage
import spock.course.lesson7app.domain.Student
import spock.course.lesson7app.enums.HomeworkType

import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

class ExampleGenerator {

    static createStudent() {
        return new Student(name: "Ivan", surname: "Ivanov");
    }

    static createHomeWork() {
        new Homework(student: createStudent(),
                number: 1,
                createDateTime: LocalDateTime.now(),
                data: "details my homework".getBytes(StandardCharsets.UTF_8)
        )
    }

    static createHomeworkMessage() {
        return new HomeworkMessage(
                homeworkType: HomeworkType.NEW_WORK,
                header: "my first homework",
                description: "description of my homework".getBytes(StandardCharsets.UTF_8),
                sentDateTime: LocalDateTime.now(),
                data: [createHomeWork()])
    }
}
