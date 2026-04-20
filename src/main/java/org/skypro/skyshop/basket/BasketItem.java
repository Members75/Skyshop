package org.skypro.skyshop.basket;

import org.skypro.skyshop.model.product.Product;

public final class BasketItem {
    final Product product;
    private final int quantity;

    public BasketItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}