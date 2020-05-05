package com.kurylchyk.model.dao;

public interface AddDeleteDAO<T,S> {
    S insert(T t);
    void delete(T t);
}
