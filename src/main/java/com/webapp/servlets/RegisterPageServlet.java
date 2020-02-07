package com.webapp.servlets;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register", initParams = {@WebInitParam(name = "email", value = "JohnDoe@grid.com")})
public class RegisterPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String initEmail = getInitParameter("email");
        String email = req.getParameter("email");
        resp.getWriter().write("Initial email is " + initEmail);
        resp.getWriter().println();
        resp.getWriter().write("This is register page for user with email " + email);
    }
}
