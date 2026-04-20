package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private UUID id;
    protected String name;
    protected int cost;

    abstract boolean isSpecial();

    Product(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустой строкой, а также пустыми пробелами");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return name;
    }

    public abstract int getPrice();


    @Override
    @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return getName();
    }
}

