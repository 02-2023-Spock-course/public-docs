package spock.course.lesson7app.service

import spock.course.lesson7app.stream.AgeFilter
import spock.lang.Specification
import spock.util.time.MutableClock

import java.time.*

class Example11MutableClockTest extends Specification {

    static final ZONE_ID = ZoneId.of('UTC')

    def "AgeFilter reacts to time with MutableClock"() {
        given:
        ZonedDateTime defaultTime =
                ZonedDateTime.of(2018, 6, 5, 0, 0, 0, 0, ZONE_ID)
        MutableClock clock = new MutableClock(defaultTime)
        AgeFilter ageFilter = new AgeFilter(clock, 18)
        LocalDate birthday = defaultTime.minusYears(18).plusDays(1).toLocalDate()

        expect:
        !ageFilter.test(birthday)

        when:
        clock + Duration.ofDays(1)

        then:
        ageFilter.test(birthday)
    }

    def "AgeFilter reacts to time with Clock < 18 years"() {
        given:
        def instant = Instant.parse("2018-06-05T00:00:00.00Z")
        Clock clock = Clock.fixed(instant, ZONE_ID)
        AgeFilter ageFilter = new AgeFilter(clock, 18)
        def birthday = LocalDate.ofInstant(instant, ZONE_ID)

        expect:
        !ageFilter.test(birthday)
    }

    def "AgeFilter reacts to time with Clock > 18 years"() {
        given:
        def instant = Instant.parse("2018-06-05T00:00:00.00Z")
        Clock clock = Clock.fixed(instant, ZONE_ID)
        AgeFilter ageFilter = new AgeFilter(clock, 18)
        def birthday = LocalDate.ofInstant(instant, ZONE_ID).minusYears(18).minusDays(1)

        expect:
        ageFilter.test(birthday)
    }
}
