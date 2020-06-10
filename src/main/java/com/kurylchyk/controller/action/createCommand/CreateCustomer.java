package com.kurylchyk.controller.action.createCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCustomer implements Create {
    private CustomerService customerService = new BusinessServiceFactory().forCustomer();
    private RequestDispatcher requestDispatcher;
    private Customer customer;

    @Override
    public RequestDispatcher create(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        try {
            List<String> violations = customerService.validate(name, surname, phoneNumber);
            if (!violations.isEmpty()) {
                setAttributeBack(name, surname, phoneNumber, req);
                req.setAttribute("violations", violations);
                req.getRequestDispatcher("customerRegistration.jsp").forward(req,resp);
                return requestDispatcher;
            }

            if (customerService.isPresent(phoneNumber)) {
                customer = customerService.getFromDB(phoneNumber);
            } else {
                customer = customerService.create(name, surname, phoneNumber);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        req.getSession().setAttribute("customer",customer);
        requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        return requestDispatcher;
    }

    private void setAttributeBack(String name, String surname, String phoneNumber, HttpServletRequest req) {
        req.setAttribute("name", name);
        req.setAttribute("surname", surname);
        req.setAttribute("phoneNumber", phoneNumber);

    }

}
