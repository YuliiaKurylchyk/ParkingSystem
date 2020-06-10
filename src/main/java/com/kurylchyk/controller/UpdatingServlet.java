package com.kurylchyk.controller;

import com.kurylchyk.controller.action.UpdateAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updatingServlet")
public class UpdatingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            RequestDispatcher requestDispatcher = new UpdateAction().execute(req,resp);
            requestDispatcher.forward(req,resp);

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }


}
