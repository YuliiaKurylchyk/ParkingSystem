package com.kurylchyk.controller;

import com.kurylchyk.controller.action.HomeAction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/*")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("In home servlet");
        showHomePage(req, resp);
    }


    private void showHomePage(HttpServletRequest req, HttpServletResponse resp) {

        //   clearSession(req.getSession());
        try {
            new HomeAction().execute(req, resp).forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

/*

    private void clearSession(HttpSession session) {

        System.out.println("in clear session!!!!!");
        if (session.getAttribute("currentTicket") != null) {
            session.removeAttribute("currentTicket");
        }
        if(session.getAttribute("action")!=null){
            session.removeAttribute("action");
        }

        if (session.getAttribute("vehicle") != null) {
            session.removeAttribute("vehicle");
        }

        if (session.getAttribute("customer") != null) {
            session.removeAttribute("customer");
        }
        if (session.getAttribute("appropriateTickets") != null) {
            session.removeAttribute("appropriateTickets");
        }
    }

 */

}
