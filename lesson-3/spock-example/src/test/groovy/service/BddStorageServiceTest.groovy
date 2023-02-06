package service

import domain.Product
import exception.StorageException
import spock.lang.Specification

/*
 * Требуется проверить тест-кейсы:
 *
 * Тест-кейс: поступление кофе-машин на склад:
 * 1. привезли  10 кофе-машин
 * 2. добавляем кофе-машины на склад в количестве 10
 * 3. делаем ревизию кофе-машин на складе
 * Результат: кофе-машин на складе должно быть 10, а в резерве 0
 *
 * Тест-кейс: два поступления кофе-машин на склад с проверкой остатка
 * 1. поступление 1-й партии
 * 2. поступление 2-й партии
 * 3. делаем ревизию кофе-машин на складе
 * Результат: кофе-машин на складе должно быть 20, а в резерве 0
 *
 * Тест-кейс: забираем кофе-машины в резерв
 * 1. имеется 10 кофе-машин
 * 2. добавить 10 на склад
 * 3. зарезервировать 10
 * 4. проверить остатки на складе
 * 5. остаток = 0, в резерве 10
 *
 * Тест-кейс: зарезервировать больше, чем есть на складе
 * 1. имеется 10 кофе-машин
 * 2. добавить 10 на склад
 * 3. зарезервировать 20
 * 4. получаем ошибку: На складе нет товара для такого резерва!
 */
class BddStorageServiceTest extends Specification {

    StorageService storageService

    def setup() {
        storageService = new StorageService()
    }

    def "поступление кофе-машин на склад"() {
        given: "привезли  10 кофе-машин"
        def storageCount = 10
        def newProduct = new Product(name: "кофе-машина")

        when: "добавляем кофе-машины на склад в количестве 10"
        storageService.addProduct(newProduct, storageCount)

        and: "делаем ревизию кофе-машин на складе"
        def storage = storageService.getStorage(newProduct)

        then:"кофе-машин на складе должно быть 10, а в резерве 0"
        storage
        storage.getStorageCount() == storageCount
        storage.getReservedCount() == 0
        noExceptionThrown()
    }

    def "два поступления кофе-машин на склад с проверкой остатка"() {
        given: "кофе-машины"
        def storageCount = 10
        def newProduct = new Product(name: "кофе-машина")

        when: "поступление 1-й партии"
        storageService.addProduct(newProduct, storageCount)

        and: "поступление 2-й партии"
        storageService.addProduct(newProduct, storageCount)

        and: "делаем ревизию кофе-машин на складе"
        def storage = storageService.getStorage(newProduct)

        then: "кофе-машин на складе должно быть 20, а в резерве 0"
        storage
        storage.getReservedCount() == 0
        storage.getStorageCount() == storageCount * 2
        noExceptionThrown()
    }

    def "забираем в резерв"() {
        given: "имеется 10 кофе-машин"
        def newProduct = new Product(name: "кофе-машина")
        def storageCount = 10

        when: "добавить 10 на склад"
        storageService.addProduct(newProduct, storageCount)

        and: "зарезервировать 10"
        storageService.reserveProduct(newProduct, storageCount)

        and: "проверить остатки на складе"
        def storage = storageService.getStorage(newProduct)

        then: "остаток = 0, в резерве 10"
        storage
        storage.getStorageCount() == 0
        storage.getReservedCount() == storageCount
        noExceptionThrown()
    }

    def "зарезервировать больше, чем есть на складе"() {
        given: "имеется 10 кофе-машин"
        def newProduct = new Product(name: "кофе-машина")
        def storageCount = 10

        when: "добавить 10 на склад"
        storageService.addProduct(newProduct, storageCount)

        and: "зарезервировать 20"
        storageService.reserveProduct(newProduct, storageCount * 2)

        then: "получаем ошибку: На складе нет товара для такого резерва!"
        def exception = thrown(StorageException)
        exception.getMessage() == "На складе нет товара для такого резерва!"
    }
}
