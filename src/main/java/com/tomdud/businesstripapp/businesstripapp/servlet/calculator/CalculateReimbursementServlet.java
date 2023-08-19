package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptTypeService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
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
    private final ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.log(Level.INFO, "CalculateReimbursementServlet::doGet");

        initializeSessionAttributesIfNullAndRecalculateSummary(request);
        request.getRequestDispatcher("reimbursement-calculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "CalculateReimbursementServlet::doPost - action - {0}", action);

        switch (action) {
            case "/calculate-reimbursement/send-to-consideration":
                ReimbursementSummary reimbursementSummary = retrieveModelOfReimbursementSummaryFromSession(request);

                //TODO
                //SAVE this and return to welcome page with list of requests?

                reimbursementService.saveReimbursementSummary(reimbursementSummary, 0L);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private void initializeSessionAttributesIfNullAndRecalculateSummary(HttpServletRequest request) {
        request.getSession().setAttribute("receiptTypes", receiptTypeService.getAllReceiptTypes());

        ReimbursementSummary reimbursementSummary = retrieveModelOfReimbursementSummaryFromSession(request);
        if (reimbursementSummary == null) {
            reimbursementSummary = ReimbursementSummary.getInitialized(reimbursementService.getLeastDetails());
            request.getSession().setAttribute("reimbursementSummary", reimbursementSummary);
        }

        reimbursementService.recalculateReimbursements(reimbursementSummary);
    }

    private ReimbursementSummary retrieveModelOfReimbursementSummaryFromSession(HttpServletRequest request) {
        return (ReimbursementSummary) request.getSession().getAttribute("reimbursementSummary");
    }

}
