package org.skypro.skyshop.basket;


import org.skypro.skyshop.model.product.Product;

import org.skypro.skyshop.model.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID productId) {
        Optional<Product> productOptional = storageService.getProductById(productId);

        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Товар с ID " + productId + " не найден");
        }

        productBasket.addProduct(productId);
    }


    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketContents = productBasket.getProducts();

        List<BasketItem> basketItems = basketContents.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new IllegalStateException("Товар в корзине отсутствует: " + productId));
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        return new UserBasket(basketItems);
    }
}