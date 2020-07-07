package com.kurylchyk.model.services.impl;

public class CommandExecutor {

    public <T> T execute(Command<T> command) throws Exception {
        return  command.execute();
    }

}
