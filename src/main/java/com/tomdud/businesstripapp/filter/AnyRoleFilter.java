package com.tomdud.businesstripapp.filter;

import com.tomdud.businesstripapp.util.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(filterName = "filter.AnyRoleFilter",
        urlPatterns = {
                "/dashboard",
                "/dashboard.jsp",
                "/navbar.jsp"
        }
)
public class AnyRoleFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AnyRoleFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.log(Level.INFO,"AnyRoleFilter::doFilter");

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("roles") == null) {
            httpResponse.sendRedirect("login");
        } else {
            Set<Role> roles = (Set<Role>) session.getAttribute("roles");
            if (roles.contains(Role.ADMIN) || roles.contains(Role.USER)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("login");
            }
        }
    }
}
