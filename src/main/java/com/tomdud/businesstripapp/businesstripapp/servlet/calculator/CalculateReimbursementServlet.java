package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.CarUsage;
import com.tomdud.businesstripapp.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.businesstripapp.model.TotalReimbursement;
import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;
import com.tomdud.businesstripapp.businesstripapp.service.DaysAllowanceService;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "CalculateReimbursementServlet",
        value = {
                "/calculate-reimbursement"
        }
)
public class CalculateReimbursementServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReceiptService receiptService = ReceiptService.getInstance();
    private final DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.log(Level.INFO, "Calculator panel get");

        request.setAttribute("receiptTypes", receiptService.getAllReceiptTypes());

        List<Receipt> receipts = (List<Receipt>) request.getSession().getAttribute("receiptList");
        if(receipts == null) {
            receipts = new ArrayList<>();
            request.getSession().setAttribute("receiptList", receipts);
        }

        TripDuration tripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
        if(tripDuration == null) {
            tripDuration = new TripDuration(LocalDate.now(), LocalDate .now(), 1, new HashSet<>());
            request.getSession().setAttribute("tripDuration", tripDuration);
        }

        CarUsage carUsage = (CarUsage) request.getSession().getAttribute("carUsage");
        if(carUsage == null) {
            carUsage = new CarUsage(0.0, reimbursementService.getLeast());
            request.getSession().setAttribute("carUsage", carUsage);
        }

        request.getSession().setAttribute("totalAllowance", daysAllowanceService.calculateTotalAllowance(tripDuration, reimbursementService.getLeast()));
        request.getSession().setAttribute("expensesTotalReimbursement", receiptService.calculateTotalReimbursement(receipts));

        TotalReimbursement totalReimbursement = new TotalReimbursement(receipts, carUsage, tripDuration);
        request.getSession().setAttribute("totalReimbursement", reimbursementService.calculateTotalReimbursement(totalReimbursement));

        request.getRequestDispatcher("reimbursement-calculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "Calculator panel post - action - {0}", action);

        switch (action) {

            default:
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;
        }


    }



}
