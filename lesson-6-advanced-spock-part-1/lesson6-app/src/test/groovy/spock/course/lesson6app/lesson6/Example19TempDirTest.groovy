package spock.course.lesson6app.lesson6

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Path

class Example19TempDirTest extends Specification {

    @Shared
    @TempDir
    File file

    @Shared
    @TempDir
    Path path

    def "first test"() {
        expect:
        file
        file instanceof File
        println()
        println(file)
        println(path)
    }

    def "second test"() {
        expect:
        file
        file instanceof File
        println()
        println(file)
        println(path)
    }
}
