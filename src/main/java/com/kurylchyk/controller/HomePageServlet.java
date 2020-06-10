package com.kurylchyk.controller;

import com.kurylchyk.controller.action.deleteCommand.DeleteCompletelyAction;
import com.kurylchyk.controller.action.HomeAction;
import com.kurylchyk.controller.action.deleteCommand.RemoveAction;
import com.kurylchyk.controller.action.SearchAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String action = req.getServletPath();
        switch (action) {
            case "/new":
                showNewForm(req, resp);
                break;
            case "/updating":
                updateEntity(req, resp);
                break;
            case "/remove":
                removeEntity(req, resp);
                break;
            case "/search":
                search(req, resp);
                break;
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/show":
                showParkingTickets(req, resp);
                break;
            case "/delete":
                deleteTicket(req, resp);
                break;
            default:
                showHomePage(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       // clearSession(req.getSession());
        RequestDispatcher dispatcher = req.getRequestDispatcher("creationServlet?action=");
        dispatcher.forward(req, resp);
    }

    private void showHomePage(HttpServletRequest req, HttpServletResponse resp) {

     //   clearSession(req.getSession());
        try {
            new HomeAction().execute(req, resp).forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showParkingTickets(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        search(request, response);
    }
    private void updateEntity(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getSession().setAttribute("action","update");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void removeEntity(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getSession().setAttribute("action","delete");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher = null;
        try {
            if(request.getSession().getAttribute("action")==null){
                request.getSession().setAttribute("action",request.getParameter("action"));
            }

            SearchAction searchAction = new SearchAction();
            requestDispatcher = searchAction.execute(request, response);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        requestDispatcher.forward(request, response);
    }

    private void deleteTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("in deleting method!!!");

        String action = (String) req.getSession().getAttribute("action");
        System.out.println("action = " + action);
        RequestDispatcher requestDispatcher = null;

        switch (action) {
            case "remove":
                try {
                    requestDispatcher = new RemoveAction().execute(req, resp);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "delete":
                try {
                    requestDispatcher = new DeleteCompletelyAction().execute(req, resp);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
        }

        requestDispatcher.forward(req, resp);

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
