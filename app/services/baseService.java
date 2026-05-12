package app.services;

import java.util.List;

/**
 * Базовый интерфейс для сервисов с CRUD операциями.
 * Все сервисы приложения реализуют этот интерфейс.
 *
 * @param <T> тип модели
 * @author Batraz2004
 * @version 1.0
 */
public interface baseService<T> {
    /** Возвращает все записи из JSON файла. */
    List<T> getAll();
    /** Сохраняет список записей в JSON файл. */
    void saveAll(List<T> items);
    /** Добавляет новую запись и сохраняет в файл. */
    void add(T item);
    /** Удаляет запись по id и сохраняет в файл. */
    void deleteById(int id);
}
