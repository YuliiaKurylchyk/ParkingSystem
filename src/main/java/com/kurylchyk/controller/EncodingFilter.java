package com.kurylchyk.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter extends HttpServlet implements Filter {

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {


        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf8");

        filterChain.doFilter(request, response);

    }
}