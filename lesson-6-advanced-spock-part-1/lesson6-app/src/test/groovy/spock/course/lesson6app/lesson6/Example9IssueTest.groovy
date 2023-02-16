package spock.course.lesson6app.lesson6

import spock.lang.Issue
import spock.lang.Specification

@Issue("http://my-jira-url/MYPROJECT-123")
class Example9IssueTest extends Specification {

    @Issue("http://my-jira-url/MYPROJECT-123")
    def "first test"() {
        expect:
        2 == 2
    }

}
