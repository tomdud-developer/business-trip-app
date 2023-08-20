package com.tomdud.businesstripapp.servlet.admin;

import com.tomdud.businesstripapp.exception.FormValueNotCorrectException;
import com.tomdud.businesstripapp.model.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.service.ReceiptTypeService;
import com.tomdud.businesstripapp.service.ReimbursementDetailsService;
import com.tomdud.businesstripapp.servlet.calculator.CalculateReimbursementServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.logging.Level;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "AdminPanelServlet", value = {"/admin-panel", "/admin-panel/update", "/admin-panel/delete-receipt", "/admin-panel/edit-receipt"})
public class AdminPanelServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    private final ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();
    private final ReimbursementDetailsService reimbursementDetailsService = ReimbursementDetailsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "AdminPanelServlet::doGet");

        initialize(request);
        request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        logger.log(Level.INFO, "AdminPanelServlet::doPost - action - {0}", action);
        try {
            switch (action) {
                case "/admin-panel/update":
                    addNewReimbursement(request);
                    response.sendRedirect(request.getContextPath() + "/admin-panel");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        } catch (Exception exception) {
            response.sendRedirect(request.getContextPath() + "/admin-panel?error=true&errorMessage=" + exception.getMessage());
            return;
        }
    }

    private void initialize(HttpServletRequest request) {
        request.setAttribute("reimbursementDetails", reimbursementDetailsService.getLeastDetails());
        request.setAttribute("reimbursementModificationList", reimbursementDetailsService.getAllDetails());
        request.setAttribute("receiptTypes", receiptTypeService.getAllReceiptTypes());
    }

    private void addNewReimbursement(HttpServletRequest request) {

        String perKilometerString = request.getParameter("perKilometer");
        String perDayString = request.getParameter("perDay");
        String mileageLimitString = request.getParameter("mileageLimit");
        String totalReimbursementLimitString = request.getParameter("totalReimbursementLimit");

        if ( perKilometerString.length() == 0
                || perDayString.length() == 0
                    || mileageLimitString.length() == 0
                        || totalReimbursementLimitString.length() == 0)
            throw new FormValueNotCorrectException("Provide correct value for new reimbursement details");

        ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO = new ReimbursementUpdateRequestDTO(
                Double.parseDouble(perKilometerString),
                Double.parseDouble(perDayString),
                request.getParameter("enableMileageLimit") != null,
                Double.parseDouble(mileageLimitString),
                request.getParameter("enableTotalReimbursementLimit") != null,
                Double.parseDouble(totalReimbursementLimitString)
        );

        reimbursementDetailsService.add(reimbursementUpdateRequestDTO);
    }

}
