package spock.course.lesson7app.stream;

import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

@AllArgsConstructor
public class AgeFilter implements Predicate<LocalDate> {

    private final Clock clock;

    private final int age;

    @Override
    public boolean test(LocalDate date) {
        return Period.between(date, LocalDate.now(clock)).getYears() >= age;
    }
}
