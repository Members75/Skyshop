package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int price;
    private final UUID id;

    public SimpleProduct(String name, int price, UUID id) {
        super(name);
        this.id = id;
        // Исправлено условие в валидации цены: было "< 0", что нелогично для цены
        if (price <= 0) {
            throw new IllegalArgumentException("Цена должна быть строго больше 0 (текущее значение: " + price + ")");
        }
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice() + " руб.";
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public UUID getId() {
        return id;
    }
}