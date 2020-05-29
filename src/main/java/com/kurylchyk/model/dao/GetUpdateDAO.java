package com.kurylchyk.model.dao;

import java.util.List;
import java.util.Optional;

public interface GetUpdateDAO<T, S> {

    Optional<T> select(S id) throws Exception;

    List<T> selectAll();

   void update(T t,S param);

}
