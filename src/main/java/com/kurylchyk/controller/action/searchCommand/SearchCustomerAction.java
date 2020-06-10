package com.kurylchyk.controller.action.searchCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//тут буде сеттатись кастомер, т.з. в рек.
public class SearchCustomerAction  implements Search{

    private CustomerService customerService = new BusinessServiceFactory().forCustomer();


    public RequestDispatcher search(HttpServletRequest req, HttpServletResponse resp) {

        Customer customer;
        String phoneNumber = req.getParameter("phoneNumber");
        try {
            customer = customerService.getFromDB(phoneNumber);
            req.getSession().setAttribute("customer",customer);
            System.out.println("Found customer "+customer);
        } catch (Exception exception) {
            req.setAttribute("notFound",exception);
            return req.getRequestDispatcher("searchPage.jsp");

        }
        return defineAction(req,resp);
}


private RequestDispatcher defineAction(HttpServletRequest req, HttpServletResponse resp)
    {
        RequestDispatcher requestDispatcher = null;
        String action = String.valueOf(req.getSession().getAttribute("action"));
        switch (action) {
            case "update":
                requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
                break;
            case "delete":
                req.getRequestDispatcher("delete?action=remove");
                break;
        }
       return requestDispatcher;
    }
}

