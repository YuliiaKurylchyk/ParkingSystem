package com.kurylchyk.controller.action.searchCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Search {
    RequestDispatcher search(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
