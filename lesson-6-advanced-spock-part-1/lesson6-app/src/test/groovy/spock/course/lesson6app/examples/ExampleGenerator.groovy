package spock.course.lesson6app.examples

import spock.course.lesson6app.domain.Student
import spock.course.lesson6app.domain.StudyGroup

class ExampleGenerator {

    static StudyGroup createStudyGroup() {
        return new StudyGroup(name: ExampleConst.GROUP_NAME,
        students: [createStudent1(), createStudent2()])
    }

    static Student createStudent1() {
        return new Student(name: "Ivan", surname: "Ivanov")
    }

    static Student createStudent2() {
        return new Student(name: "Ivan", surname: "Ivanov")
    }
}
