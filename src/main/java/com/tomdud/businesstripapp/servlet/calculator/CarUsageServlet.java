package com.tomdud.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.exception.FormValueNotCorrectException;
import com.tomdud.businesstripapp.model.CarUsage;
import com.tomdud.businesstripapp.model.ReimbursementSummary;
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

        try {
            switch (action) {
                case "/calculate-reimbursement/modify-car-usage":
                    calculateCarUsage(request);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        } catch (Exception exception) {
            response.sendRedirect(request.getContextPath() + "/calculate-reimbursement?error=true&errorMessage=" + exception.getMessage());
            return;
        }

        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    private void calculateCarUsage(HttpServletRequest request) {
        if (request.getParameter("distance") == null || request.getParameter("distance").length() == 0)
            throw new FormValueNotCorrectException("Provide correct distance");

        double distance = Double.parseDouble(request.getParameter("distance"));
        CarUsage carUsage = retrieveModelOfCarUsageFromSession(request);
        carUsage.setDistance(distance);
    }

    private CarUsage retrieveModelOfCarUsageFromSession(HttpServletRequest request) {
        return ((ReimbursementSummary) request.getSession().getAttribute("reimbursementSummary")).getCarUsage();
    }
}
