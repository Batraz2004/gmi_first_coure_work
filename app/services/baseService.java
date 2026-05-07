package app.services;

import java.util.List;

/**
 * Базовый интерфейс для сервисов с CRUD операциями.
 *
 * @param <T> тип модели
 */
public interface baseService<T> {
    List<T> getAll();
    void saveAll(List<T> items);
    void add(T item);
    void deleteById(int id);
}
