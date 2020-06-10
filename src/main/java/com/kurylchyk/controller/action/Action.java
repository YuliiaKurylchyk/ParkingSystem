package com.kurylchyk.controller.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
