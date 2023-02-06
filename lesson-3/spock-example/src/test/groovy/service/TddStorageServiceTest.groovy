package service

import domain.Product
import exception.StorageException
import service.StorageService
import spock.lang.Specification

/**
 * TDD
 */
class TddStorageServiceTest extends Specification {

    StorageService storageService

    def setup() {
        storageService = new StorageService()
    }

    def "add new product"() {
        given:
        def storageCount = 10
        def newProduct = new Product(name: "кофе-машина")

        when:
        storageService.addProduct(newProduct, storageCount)
        def storage = storageService.getStorage(newProduct)

        then:
        storage
        storage.getReservedCount() == 0
        storage.getStorageCount() == storageCount
        noExceptionThrown()
    }

    def "add existing product with no exception"() {
        given:
        def storageCount = 10
        def newProduct = new Product(name: "кофе-машина")

        when:
        storageService.addProduct(newProduct, storageCount)
        storageService.addProduct(newProduct, storageCount)
        def storage = storageService.getStorage(newProduct)

        then:
        storage
        storage.getReservedCount() == 0
        storage.getStorageCount() == storageCount * 2
        noExceptionThrown()
    }

    def "reserve product"() {
        given:
        def newProduct = new Product(name: "кофе-машина")
        def storageCount = 10

        when:
        storageService.addProduct(newProduct, storageCount)
        storageService.reserveProduct(newProduct, storageCount)
        def storage = storageService.getStorage(newProduct)

        then:
        storage
        storage.getStorageCount() == 0
        storage.getReservedCount() == storageCount
        noExceptionThrown()
    }

    def "reserve above max-count product"() {
        given:
        def newProduct = new Product(name: "кофе-машина")
        def storageCount = 10

        when:
        storageService.addProduct(newProduct, storageCount)
        storageService.reserveProduct(newProduct, storageCount * 2)

        then:
        def exception = thrown(StorageException)
        exception.getMessage() == "На складе нет товара для такого резерва!"
    }
}
