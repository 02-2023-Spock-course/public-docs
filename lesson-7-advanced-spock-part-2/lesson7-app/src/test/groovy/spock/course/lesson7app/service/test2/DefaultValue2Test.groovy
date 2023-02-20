package spock.course.lesson7app.service.test2

import spock.course.lesson7app.service.ErrorHandlerService
import spock.lang.Specification

/**
 *
 * org.spockframework.mock.runtime.JavaMockInterceptor
 *
 * типизация org.spockframework.mock.MockNature
 *
 * Stub:
 * org.spockframework.mock.EmptyOrDummyResponse
 *
 * Mock:
 * org.spockframework.mock.ZeroOrNullResponse
 */
class DefaultValue2Test extends Specification {

    ErrorHandlerService mock = Mock()
    ErrorHandlerService stub = Stub()

    def "int test"() {
        expect:
        mock.getInt() == 0
        mock.getInt() != null
        stub.getInt() == 0
    }

    def "String test"() {
        expect:
        mock.getString() == null
        stub.getString() == ""
    }

    def "List test"() {
        expect:
        mock.getList() == null
        stub.getList() == new ArrayList()
        stub.getList() !== new ArrayList()
        stub.getList() instanceof ArrayList
        stub.getList() instanceof List
    }
}
