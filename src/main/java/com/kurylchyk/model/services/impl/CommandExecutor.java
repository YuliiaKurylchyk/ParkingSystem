package com.kurylchyk.model.services.impl;

public class CommandExecutor {

    <T> T execute(Command<T> cmd) throws Exception {
        return  cmd.execute();
    }

}
