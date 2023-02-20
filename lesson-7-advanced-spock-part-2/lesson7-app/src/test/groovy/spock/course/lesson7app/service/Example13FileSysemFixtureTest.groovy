package spock.course.lesson7app.service


import spock.lang.Specification
import spock.lang.TempDir
import spock.util.io.FileSystemFixture

import java.nio.file.Files

class Example13FileSysemFixtureTest extends Specification {

    @TempDir
    FileSystemFixture fsFixture

    def "FileSystemFixture can create a directory structure"() {
        when:
        fsFixture.create {
            dir('src') {
                dir('main') {
                    dir('groovy') {
                        file('HelloWorld.java') << 'println "Hello World"'
                    }
                }
                dir('test/resources') {
                    file('META-INF/MANIFEST.MF') << 'bogus entry'
                }
            }
        }

            then:
            Files.isDirectory(fsFixture.resolve('src/main/groovy'))
            Files.isDirectory(fsFixture.resolve('src/test/resources/META-INF'))
            fsFixture.resolve('src/main/groovy/HelloWorld.java').text == 'println "Hello World"'
            fsFixture.resolve('src/test/resources/META-INF/MANIFEST.MF').text == 'bogus entry'
        }
}