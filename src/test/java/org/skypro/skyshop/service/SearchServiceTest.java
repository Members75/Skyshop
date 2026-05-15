package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.service.SearchService;
import org.skypro.skyshop.model.service.StorageService;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private List<Searchable> testSearchableData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Создаём тестовые данные для мока
        testSearchableData = Arrays.asList(
                createSearchable("Обзор компьютеров 2023", UUID.randomUUID()),
                createSearchable("Клавиатура", UUID.randomUUID()),
                createSearchable("Компьютер", UUID.randomUUID())
        );
    }

    // Вспомогательный метод для создания Searchable объектов
    private Searchable createSearchable(String name, UUID id) {
        return new Searchable() {
            @Override
            public String getSearchTerm() {
                return "";
            }

            @Override
            public String getContentType() {
                return "";
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public UUID getId() {
                return id;
            }
        };
    }

    /**
     * Сценарий 1: Поиск в случае отсутствия объектов в StorageService
     */
    @Test
    void search_WhenNoObjectsInStorage_ShouldReturnEmptyList() {
        // Given: Мок возвращает пустой список
        when(storageService.getAllSearchable()).thenReturn(Collections.emptyList());

        // When: Выполняем поиск
        Collection<SearchResult> results = searchService.search("любой_паттерн");

        // Then: Результат должен быть пустым
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchable();
    }

    /**
     * Сценарий 2: Поиск в случае, если объекты есть, но нет подходящего по паттерну
     */
    @Test
    void search_WhenObjectsExistButNoMatch_ShouldReturnEmptyList() {
        // Given: Мок возвращает данные, но ни один объект не содержит паттерн
        when(storageService.getAllSearchable()).thenReturn(testSearchableData);

        // When: Ищем по паттерну, которого нет в данных
        Collection<SearchResult> results = searchService.search("несуществующий_паттерн");

        // Then: Результат должен быть пустым
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchable();
    }

    /**
     * Сценарий 3: Поиск, когда есть подходящий объект в StorageService
     */
    @Test
    void search_WhenMatchingObjectExists_ShouldReturnResults() {
        // Given: Мок возвращает тестовые данные
        when(storageService.getAllSearchable()).thenReturn(testSearchableData);

        // When: Ищем по паттерну "Компьютер"
        Collection<SearchResult> results = searchService.search("Компьютер");

        // Then: Должен найти один объект
        assertNotNull(results);
        assertEquals(2, results.size()); // "Компьютер" и "Обзор компьютеров 2023"

        List<String> resultNames = results.stream()
                .map(SearchResult::getName)
                .collect(Collectors.toList());

        assertTrue(resultNames.contains("Компьютер"));
        assertTrue(resultNames.contains("Обзор компьютеров 2023"));
        verify(storageService, times(1)).getAllSearchable();
    }

    /**
     * Сценарий 4: Поиск с пустым паттерном
     */
    @Test
    void search_WithEmptyPattern_ShouldReturnAllObjects() {
        // Given: Мок возвращает тестовые данные
        when(storageService.getAllSearchable()).thenReturn(testSearchableData);

        // When: Ищем с пустым паттерном (должен вернуть все объекты)
        Collection<SearchResult> results = searchService.search("");

        // Then: Должны вернуть все объекты
        assertNotNull(results);
        assertEquals(testSearchableData.size(), results.size());
        verify(storageService, times(1)).getAllSearchable();
    }
}
