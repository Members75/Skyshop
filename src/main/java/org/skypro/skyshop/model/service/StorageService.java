package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initTestData();
    }

    private void initTestData() {
        Product product1 = new SimpleProduct("Компьютер", 87400, UUID.randomUUID());
        Product product2 = new SimpleProduct("Клавиатура", 3500, UUID.randomUUID());
        Product product3 = new SimpleProduct("Игровое кресло", 12300, UUID.randomUUID());
        Product product4 = new SimpleProduct("Мышь", 2700, UUID.randomUUID());
        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);
        products.put(product4.getId(), product4);

        Article article1 = new Article("Обзор компьютеров 2023", "Подробный обзор моделей компьютеров 2023 года...", UUID.randomUUID());
        Article article2 = new Article("Как выбрать клавиатуру", "Рекомендации по выбору клавиатуры: тип, цена, размер...", UUID.randomUUID());
        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);


    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchable() {
        return Stream.concat(products.values().stream(),
                articles.values().stream()).collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}
