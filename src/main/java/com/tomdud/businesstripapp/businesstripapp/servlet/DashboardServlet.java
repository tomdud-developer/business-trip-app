package com.tomdud.businesstripapp.businesstripapp.servlet;

import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementDetailsService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import com.tomdud.businesstripapp.businesstripapp.servlet.calculator.CalculateReimbursementServlet;
import com.tomdud.businesstripapp.businesstripapp.util.SampleDataGenerator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReimbursementDetailsService reimbursementDetailsService = ReimbursementDetailsService.getInstance();
    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = 0L;//(long) request.getSession().getAttribute("id");

        logger.log(Level.INFO, "DashboardServlet::doGet");
        List<ReimbursementSummary> reimbursementSummaries = generateSampleData(userId);
        reimbursementSummaries.addAll(reimbursementService.getAllReimbursementSummariesByUserId(userId));

        request.setAttribute("reimbursementSummaryList", reimbursementSummaries);
        request.setAttribute("reimbursementModificationList", reimbursementDetailsService.getAllDetails());

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private List<ReimbursementSummary> generateSampleData(long userId) {
        List<ReimbursementSummary> reimbursementSummaries = new ArrayList<>();

        SampleDataGenerator sampleDataGenerator = new SampleDataGenerator();
        ReimbursementSummary reimbursementSummary = sampleDataGenerator.getReimbursementSummary();
        reimbursementSummary.setUserId(userId);
        reimbursementSummary.setId(0L);
        reimbursementSummaries.add(reimbursementSummary);

        reimbursementSummary = sampleDataGenerator.getReimbursementSummary();
        reimbursementSummary.setUserId(userId);
        reimbursementSummary.setId(1L);
        reimbursementSummaries.add(reimbursementSummary);

        reimbursementSummary = sampleDataGenerator.getReimbursementSummary();
        reimbursementSummary.setUserId(userId);
        reimbursementSummary.setId(2L);
        reimbursementSummaries.add(reimbursementSummary);

        reimbursementSummary = sampleDataGenerator.getReimbursementSummary();
        reimbursementSummary.setUserId(userId);
        reimbursementSummary.setId(3L);
        reimbursementSummaries.add(reimbursementSummary);

        return reimbursementSummaries;
    }
}
