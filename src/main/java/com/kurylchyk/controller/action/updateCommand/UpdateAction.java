package com.kurylchyk.controller.action.updateCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UpdateAction {
    RequestDispatcher update(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
