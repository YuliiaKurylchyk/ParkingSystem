package com.kurylchyk.model.service;

import java.util.List;

public interface ServiceGetUpdate<T,S> {

    T get(S s) throws Exception;
    List<T> getAll();
    void update(T t,S s);
}
