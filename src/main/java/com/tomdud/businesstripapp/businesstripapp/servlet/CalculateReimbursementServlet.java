package com.tomdud.businesstripapp.businesstripapp.servlet;

import com.tomdud.businesstripapp.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "CalculateReimbursementServlet",
        value = {"/calculate-reimbursement", "/calculate-reimbursement/add-receipt"}
)
public class CalculateReimbursementServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReceiptService receiptService = ReceiptService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.log(Level.INFO, "Calculator panel get");

        request.setAttribute("reimbursement", reimbursementService.getLeast());
        request.setAttribute("receiptTypes", receiptService.getAllReceiptTypes());

        List<Receipt> receipts = (List<Receipt>) request.getSession().getAttribute("receiptList");
        if(receipts == null) {
            receipts = new ArrayList<>();
            request.getSession().setAttribute("receiptList", receipts);
        }

        TripDuration tripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
        if(tripDuration == null) {
            tripDuration = new TripDuration(LocalDate.now(), LocalDate .now(), 1);
            request.getSession().setAttribute("tripDuration", tripDuration);
        }

        request.getSession().setAttribute("totalAllowance", reimbursementService.calculateTotalAllowance(tripDuration.getDuration()));
        request.getSession().setAttribute("expensesTotalReimbursement", receiptService.calculateTotalReimbursement(receipts));
        request.getRequestDispatcher("reimbursement-calculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "Calculator panel post - action - {0}", action);

        switch (action) {
            case "/calculate-reimbursement/add-receipt":
            case "calculate-reimbursement/add-receipt":

                logger.log(Level.INFO, "CalculatorPanel::doPost - adding new receipt - {0}",
                        request.getParameter("receiptType"));

                Receipt receiptToAdd = new Receipt(
                        Double.parseDouble(request.getParameter("receiptValue")),
                        receiptService.getReceiptTypeByName(request.getParameter("receiptType"))
                );


                ArrayList<Receipt> receiptArrayList = (ArrayList<Receipt>) request.getSession().getAttribute("receiptList");
                receiptArrayList.add(receiptToAdd);

                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;
        }


    }



}
