package org.civilla.storage;

public interface IStorageConnector <T> {
    T get(String id);
    String update(T entity);
    String delete(String id);
}
