package org.skypro.skyshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductBasket {
    private final UUID id;
    private final Map<String, List<Product>> productsMap = new HashMap<>();

    public ProductBasket(UUID id) {
        this.id = id;
    }

    void addProduct(Product product) {
        if (product == null) return;

        String name = product.getName();
        if (!productsMap.containsKey(name)) {
            productsMap.put(name, new ArrayList<>());
        }
        productsMap.get(name).add(product);
    }

    List<Product> removeProductsByName(String name) {
        List<Product> removed = productsMap.remove(name);
        if (removed == null) {
            return new ArrayList<>();
        }
        return removed;
    }

    void printBasket() {
        System.out.println("Содержимое корзины");
        productsMap.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(product -> System.out.println("- " + product.getName() + " - " + product.getPrice() + " рублей."));
    }

    Collection<Product> getAllProducts() {
        return productsMap.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    double calculateTotalPrice() {
        return productsMap.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
