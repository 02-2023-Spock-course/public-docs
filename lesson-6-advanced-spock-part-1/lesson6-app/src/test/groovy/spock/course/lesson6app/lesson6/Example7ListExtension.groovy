package spock.course.lesson6app.lesson6

class Example7ListExtension {

    static avg(List list) {
        list.sum() / list.size()
    }

    //stub
    static isEmpty(List list) {
        list.size() < 5
    }
}
