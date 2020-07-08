package com.kurylchyk.model.dao;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface DAO<T, S> {
    S insert(T t);

    void delete(T t);

    Optional<T> select(S id) throws Exception;

    List<T> selectAll();

    void update(T t, S param);

}
