package com.kurylchyk.model.services.impl;

public interface Command<T> {

    T execute() throws Exception;

}