package service;

import domain.Product;
import domain.Storage;
import exception.StorageException;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class StorageService {

    private final Map<Product, Storage> products = new HashMap<>();

    public void addProduct(Product product, int count) {
        if(!products.containsKey(product)) {
            initProduct(product, count);
        } else {
            var storage = getStorage(product);
            storage.setStorageCount(storage.getStorageCount() + count);
        }
    }

    public void reserveProduct(Product product, int reserveCount) {
        var storage = getStorage(product);
        if (storage.getStorageCount() >= reserveCount) {
            storage.setStorageCount(storage.getStorageCount() - reserveCount);
            storage.setReservedCount(storage.getReservedCount() + reserveCount);
        } else {
            throw new StorageException("На складе нет товара для такого резерва!");
        }
    }

    public Storage getStorage(Product product) {
        return products.get(product);
    }

    public boolean isProductExists(Product product) {
        return products.containsKey(product);
    }

    private void initProduct(Product product, int count) {
        products.put(product, new Storage(count, 0));
    }
}
