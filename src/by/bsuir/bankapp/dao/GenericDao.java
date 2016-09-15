package by.bsuir.bankapp.dao;

import java.util.List;

public interface GenericDao<K, T> {
    T read (K key);
    void update(T obj);
    void delete(K key);
    void create(T obj);
    List<T> readAll();
}
