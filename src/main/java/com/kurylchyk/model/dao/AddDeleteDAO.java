package com.kurylchyk.model.dao;

public interface AddDeleteDAO<T> {
    void add(T t);
    void delete(T t);
}
