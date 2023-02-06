package service

import domain.Product
import spock.lang.Specification
import spock.lang.Unroll

class DddStorageServiceTest extends Specification {

    StorageService storageService

    def setup() {
        storageService = new StorageService()
    }

    @Unroll("добавляем #addCount1 и #addCount2, затем резервируем #forReserv1, проверяем что в резерве #reserved и осталось на складе #finishCount")
    def "parametrized test for multiple addition of products"() {
        given:
        def product = new Product(name: "кофе-машина")

        when:
        storageService.addProduct(product, addCount1)
        storageService.addProduct(product, addCount2)
        storageService.reserveProduct(product, forReserv1)
        def storage = storageService.getStorage(product)

        then:
        storage.getReservedCount() == reserved
        storage.getStorageCount() == finishCount
        noExceptionThrown()

        where:
        addCount1    << [10, 6, 10]
        addCount2    << [10, 3, 10]
        forReserv1   << [20, 4, 0]
        reserved     << [20, 4, 0]
        finishCount  << [0,  5, 20]
    }
}
