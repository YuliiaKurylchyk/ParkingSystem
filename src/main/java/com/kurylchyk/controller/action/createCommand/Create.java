package com.kurylchyk.controller.action.createCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Create {
    RequestDispatcher create(HttpServletRequest req, HttpServletResponse resp);
}
