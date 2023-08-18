package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.CarUsage;
import com.tomdud.businesstripapp.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
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
                "/calculate-reimbursement",
                "/calculate-reimbursement/send-to-consideration"
        }
)
public class CalculateReimbursementServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReceiptService receiptService = ReceiptService.getInstance();
    private final DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.log(Level.INFO, "CalculateReimbursementServlet::doGet");

        initializeSessionAttributesIfNullsAndCalculateTotals(request);
        request.getRequestDispatcher("reimbursement-calculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "CalculateReimbursementServlet::doPost - action - {0}", action);

        switch (action) {
            case "/calculate-reimbursement/send-to-consideration":
                ReimbursementSummary reimbursementSummary = buildTotalReimbursement(request);
                reimbursementSummary.setUserId(123L);

                //TODO
                //SAVE this and return to welcome page with list of requests?

                reimbursementService.saveReimbursementSummary(reimbursementSummary, 0L);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private void initializeSessionAttributesIfNullsAndCalculateTotals(HttpServletRequest request) {
        request.getSession().setAttribute("receiptTypes", receiptService.getAllReceiptTypes());

        List<Receipt> receipts = retrieveListOfReceiptsFromSession(request);
        if(receipts == null) {
            receipts = new ArrayList<>();
            request.getSession().setAttribute("receiptList", receipts);
        }

        TripDuration tripDuration = retrieveTripDurationFromSession(request);
        if(tripDuration == null) {
            tripDuration = new TripDuration(LocalDate.now(), LocalDate .now(), 1, new HashSet<>());
            request.getSession().setAttribute("tripDuration", tripDuration);
        }

        CarUsage carUsage = retrieveCarUsageFromSession(request);
        if(carUsage == null) {
            carUsage = new CarUsage(0.0, reimbursementService.getLeast());
            request.getSession().setAttribute("carUsage", carUsage);
        }

        calculateTotals(request);
    }

    private void calculateTotals(HttpServletRequest request) {
        ReimbursementSummary reimbursementSummary = buildTotalReimbursement(request);

        request.getSession().setAttribute("totalAllowance", daysAllowanceService.calculateTotalAllowance(reimbursementSummary.getTripDuration(), reimbursementService.getLeast()));
        request.getSession().setAttribute("expensesTotalReimbursement", receiptService.calculateTotalReimbursement(reimbursementSummary.getReceiptList()));
        request.getSession().setAttribute("totalReimbursement", reimbursementSummary.getTotalReimbursement());
    }

    private ReimbursementSummary buildTotalReimbursement(HttpServletRequest request) {
        return reimbursementService.calculateTotalReimbursement(
                retrieveTripDurationFromSession(request),
                retrieveListOfReceiptsFromSession(request),
                retrieveCarUsageFromSession(request)
        );
    }

    private List<Receipt> retrieveListOfReceiptsFromSession(HttpServletRequest request) {
        return (List<Receipt>) request.getSession().getAttribute("receiptList");
    }

    private TripDuration retrieveTripDurationFromSession(HttpServletRequest request) {
        return (TripDuration) request.getSession().getAttribute("tripDuration");
    }

    private CarUsage retrieveCarUsageFromSession(HttpServletRequest request) {
        return (CarUsage) request.getSession().getAttribute("carUsage");
    }



}
