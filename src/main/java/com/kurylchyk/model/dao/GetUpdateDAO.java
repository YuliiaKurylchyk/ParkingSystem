package com.kurylchyk.model.dao;

import java.util.List;
import java.util.Optional;

public interface GetUpdateDAO<T, S> {

    T get(S id) throws Exception;

    List<T> getAll();


   void update(T t, S param);



}
