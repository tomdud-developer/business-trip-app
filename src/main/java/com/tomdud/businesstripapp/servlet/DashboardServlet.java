package com.tomdud.businesstripapp.servlet;

import com.tomdud.businesstripapp.model.ReimbursementDetails;
import com.tomdud.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.service.ReimbursementDetailsService;
import com.tomdud.businesstripapp.service.ReimbursementService;
import com.tomdud.businesstripapp.servlet.calculator.CalculateReimbursementServlet;
import com.tomdud.businesstripapp.util.Role;
import com.tomdud.businesstripapp.util.SampleDataGenerator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReimbursementDetailsService reimbursementDetailsService = ReimbursementDetailsService.getInstance();
    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "DashboardServlet::doGet");

        List<ReimbursementSummary> reimbursementSummaries;
        Role role = (Role) request.getSession().getAttribute("role");

        if (role.equals(Role.ADMIN)) {
            reimbursementSummaries = reimbursementService.getAllFromAllUsers();
        } else {
            long userId = (long) request.getSession().getAttribute("id");
            reimbursementSummaries = reimbursementService.getAllReimbursementSummariesByUserId(userId);
        }

        reimbursementSummaries = reimbursementSummaries.stream()
                .sorted(Comparator.comparing(ReimbursementSummary::getCreationDateTime).reversed())
                .collect(Collectors.toList());

        request.setAttribute("reimbursementSummaryList", reimbursementSummaries);
        request.setAttribute("reimbursementModificationList", reimbursementDetailsService.getAllDetails());

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
