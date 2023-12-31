package com.tomdud.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.exception.FormValueNotCorrectException;
import com.tomdud.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.service.ReceiptTypeService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "ReceiptServlet",
        value = {"/calculate-reimbursement/add-receipt","/calculate-reimbursement/delete-receipt"}
)
public class ReceiptServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ReceiptServlet.class.getName());
    private final ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "ReceiptServlet::doGet - redirect to calculator");
        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        logger.log(Level.INFO, "ReceiptServlet::doPost - action - {0}", action);
        try {
            switch (action) {
                case "/calculate-reimbursement/add-receipt":
                    addReceipt(request);
                    break;
                case "/calculate-reimbursement/delete-receipt":
                    removeReceipt(request);
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

    private void addReceipt(HttpServletRequest request) {
        List<Receipt> receiptArrayList = retrieveModelOfReceiptsFromSession(request);

        logger.log(Level.INFO, "ReceiptServlet::doPost - adding new receipt - {0}",
                request.getParameter("receiptType"));

        String receiptValueString = request.getParameter("receiptValue");
        if ( receiptValueString == null || receiptValueString.length() == 0)
            throw new FormValueNotCorrectException("Provide correct data for receipt");

        Double receiptValue = Double.parseDouble(receiptValueString);

        Receipt receiptToAdd = new Receipt(
                receiptValue,
                receiptTypeService.getReceiptTypeByName(request.getParameter("receiptType"))
        );

        receiptArrayList.add(receiptToAdd);
    }

    private void removeReceipt(HttpServletRequest request) {
        List<Receipt> receiptArrayList = retrieveModelOfReceiptsFromSession(request);

        int indexToDelete = Integer.parseInt(request.getParameter("receiptIndexToDelete"));
        logger.log(Level.INFO, "ReceiptServlet::doPost - deleting receipt with index - {0}", indexToDelete);

        receiptArrayList.remove(indexToDelete);
    }

    private List<Receipt> retrieveModelOfReceiptsFromSession(HttpServletRequest request) {
        return ((ReimbursementSummary) request.getSession().getAttribute("reimbursementSummary"))
                .getReceiptList();
    }
}
