package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.ParkingSystemException;

public interface Command<T> {

    T execute() throws ParkingSystemException;

}