package com.kurylchyk.model.service;
import java.util.List;

public interface ServiceAddDelete<T,S> {


    S add(T t);
    void delete(T t);
    boolean isPresent(S s);

}
