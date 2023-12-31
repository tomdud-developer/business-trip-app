package com.tomdud.businesstripapp.filter;

import com.tomdud.businesstripapp.util.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(filterName = "filter.UserRoleFilter",
        urlPatterns = {
                "/calculate-reimbursement",
                "/calculate-reimbursement-new-calculation",
                "/calculate-reimbursement/*",
                "/reimbursement-calculator.jsp",
        }
)
public class UserRoleFilter implements Filter {

    private static final Logger logger = Logger.getLogger(UserRoleFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.log(Level.INFO,"FilterUserRoleFilter::doFilter");

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            httpResponse.sendRedirect("login");
        } else {
            Role role = (Role) session.getAttribute("role");
            if (role.equals(Role.USER)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("login");
            }
        }
    }
}
