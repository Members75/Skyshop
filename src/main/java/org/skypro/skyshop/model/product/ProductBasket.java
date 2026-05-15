package org.skypro.skyshop.model.product;

import org.skypro.skyshop.model.service.NoSuchProductException.NoSuchProductException;
import org.skypro.skyshop.model.service.StorageService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductBasket {

    private final Map<UUID, Integer> products = new HashMap<>();
    private final StorageService productService;

    public ProductBasket(StorageService productService) {
        this.productService = productService;
    }

    public void addProduct(UUID productId) {
        if (productId == null) {
            return;
        }

        if (!productService.existsById(productId)) {
            throw new NoSuchProductException("Продукт с UUID " + productId + " не найден");
        }


        products.merge(productId, 1, Integer::sum);
    }

    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}