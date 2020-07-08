package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.ParkingSystemException;

public class CommandExecutor {

    public <T> T execute(Command<T> command) throws ParkingSystemException {
        return  command.execute();
    }

}
