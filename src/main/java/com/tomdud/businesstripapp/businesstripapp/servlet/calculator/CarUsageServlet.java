package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.CarUsage;
import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CarUsageServlet", value = "/calculate-reimbursement/modify-car-usage")
public class CarUsageServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CarUsageServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "CarUsageServlet::doGet - redirect to calculator");
        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        logger.log(Level.INFO, "CarUsageServlet::doPost - action - {0}", action);

        switch (action) {
            case "/calculate-reimbursement/modify-car-usage":
                calculateCarUsage(request);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }

        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    private void calculateCarUsage(HttpServletRequest request) {
        double distance = Double.parseDouble(request.getParameter("distance"));
        CarUsage carUsage = retrieveModelOfCarUsageFromSession(request);
        carUsage.setDistance(distance);
    }

    private CarUsage retrieveModelOfCarUsageFromSession(HttpServletRequest request) {
        return ((ReimbursementSummary) request.getSession().getAttribute("reimbursementSummary")).getCarUsage();
    }
}
