package com.webapp.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String command = req.getParameter("command");
        if (command == null) {
            command = "";
        }
        if (command.equals("register")) {
            resp.sendRedirect("/WEB_dkropotov2_war_exploded/register");
        }
        if (command.equals("login")) {
            resp.sendRedirect("/WEB_dkropotov2_war_exploded/login");
        }
        PrintWriter writer = resp.getWriter();
        writer.write("Please write command in param");
    }
}
