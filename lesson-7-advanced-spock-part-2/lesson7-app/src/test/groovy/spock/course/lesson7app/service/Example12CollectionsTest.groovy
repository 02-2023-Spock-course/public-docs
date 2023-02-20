package spock.course.lesson7app.service


import spock.lang.Specification

class Example12CollectionsTest extends Specification {

    def "classic match - all elements contains"() {
        given:
        def mySet = [1, 3, 2] as Set

        expect:
        mySet == [1, 2, 3] as Set
        mySet == [1, 3, 2] as Set
        mySet != [1, 2, 3, 4] as Set
    }

    def "lenient match - all elements contains - any order"() {
        expect: "проверка вхождения без учёта кол-ва"
        [1, 2, 3, 3] =~ [1, 2, 3, 3, 3, 3]
    }

    def "strict match - all elements contains - any order"() {
        expect: "проверка вхождения с учётом кол-ва"
        [1, 2, 3, 3] ==~ [1, 2, 3, 3]
    }
}
