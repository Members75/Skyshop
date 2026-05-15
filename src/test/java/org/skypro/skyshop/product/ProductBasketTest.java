package org.skypro.skyshop.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.skyshop.model.product.ProductBasket;
import org.skypro.skyshop.model.service.NoSuchProductException.NoSuchProductException;
import org.skypro.skyshop.model.service.StorageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductBasketTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private ProductBasket productBasket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addProduct_WhenProductDoesNotExist_ShouldThrowNoSuchProductException() {
        // Given: Мок возвращает false для existsById (товар не существует)
        UUID nonExistingProductId = UUID.randomUUID();
        when(storageService.existsById(nonExistingProductId)).thenReturn(false);

        // When & Then: Пытаемся добавить товар и проверяем исключение
        assertThrows(NoSuchProductException.class, () -> {
            productBasket.addProduct(nonExistingProductId);
        });

        // Проверяем, что метод existsById был вызван
        verify(storageService).existsById(nonExistingProductId);
    }
    @Test
    void addProduct_WhenProductExists_ShouldAddToBasket() {
        // Given: Мок возвращает true для existsById (товар существует)
        UUID existingProductId = UUID.randomUUID();
        when(storageService.existsById(existingProductId)).thenReturn(true);

        // When: Добавляем товар в корзину
        productBasket.addProduct(existingProductId);

        // Then: Проверяем, что товар добавлен в карту products
        Map<UUID, Integer> products = productBasket.getProducts();
        assertTrue(products.containsKey(existingProductId));
        assertEquals(1, products.get(existingProductId));

        // Проверяем вызов existsById
        verify(storageService).existsById(existingProductId);
    }
    @Test
    void getUserBasket_WhenBasketIsEmpty_ShouldReturnEmptyMap() {
        // Given: Корзина изначально пуста (ничего не добавляли)

        // When: Получаем корзину пользователя
        Map<UUID, Integer> userBasket = productBasket.getProducts();

        // Then: Корзина должна быть пустой
        assertTrue(userBasket.isEmpty());
        assertEquals(0, userBasket.size());
    }
    @Test
    void getUserBasket_WhenBasketHasProducts_ShouldReturnCorrectBasket() {
        // Given: Товар существует, добавляем его в корзину
        UUID productId = UUID.randomUUID();
        when(storageService.existsById(productId)).thenReturn(true);

        // When: Добавляем товар и получаем корзину
        productBasket.addProduct(productId);
        Map<UUID, Integer> userBasket = productBasket.getProducts();

        // Then: Корзина должна содержать добавленный товар
        assertTrue(userBasket.containsKey(productId));
        assertEquals(1, userBasket.get(productId));
        assertEquals(1, userBasket.size());

        // Проверяем вызов existsById
        verify(storageService).existsById(productId);
    }
}
