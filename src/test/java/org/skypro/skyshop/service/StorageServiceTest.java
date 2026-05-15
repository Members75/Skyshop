package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.skyshop.model.product.ProductBasket;
import org.skypro.skyshop.model.service.StorageService;

public class StorageServiceTest {
    @Mock
    private StorageService storageService;

    @InjectMocks
    private ProductBasket productBasket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
