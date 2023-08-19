package com.tomdud.businesstripapp.servlet;

import com.tomdud.businesstripapp.exception.UserNotAuthenticatedException;
import com.tomdud.businesstripapp.model.User;
import com.tomdud.businesstripapp.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", value = {
        "/login", "/logout"
})
public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "LoginServlet::doGet");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        logger.log(Level.INFO, "LoginServlet::doPost - action -{0}", action);

        switch (action) {
            case "/login":
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                logger.log(Level.INFO, "LoginServlet::doPost::login username - {0}", username);

                try {
                    User user = userService.returnUserIfPasswordIsCorrect(username, password);

                    request.getSession().setAttribute("username", user.getUsername());
                    request.getSession().setAttribute("roles", user.getRoles());
                    request.getSession().setAttribute("id", user.getId());

                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } catch (UserNotAuthenticatedException e) {
                    request.setAttribute("error", "Incorrect username or password");
                    doGet(request, response);
                }

                break;
            case "/logout":
                logger.log(Level.INFO, "LoginServlet::doPost::logout");

                request.getSession().setAttribute("username", null);
                request.getSession().setAttribute("roles", null);
                request.getSession().setAttribute("id", null);

                doGet(request, response);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }

    }
}
