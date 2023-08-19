package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptService;
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
    private final ReceiptService receiptService = ReceiptService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "ReceiptServlet::doGet - redirect to calculator");
        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        logger.log(Level.INFO, "ReceiptServlet::doPost - action - {0}", action);

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

        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    private void addReceipt(HttpServletRequest request) {
        List<Receipt> receiptArrayList = retrieveModelOfReceiptsFromSession(request);

        logger.log(Level.INFO, "ReceiptServlet::doPost - adding new receipt - {0}",
                request.getParameter("receiptType"));

        Receipt receiptToAdd = new Receipt(
                Double.parseDouble(request.getParameter("receiptValue")),
                receiptService.getReceiptTypeByName(request.getParameter("receiptType"))
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
