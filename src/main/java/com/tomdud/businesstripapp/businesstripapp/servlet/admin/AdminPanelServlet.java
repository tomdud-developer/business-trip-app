package com.tomdud.businesstripapp.businesstripapp.servlet.admin;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptTypeService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import com.tomdud.businesstripapp.businesstripapp.servlet.calculator.CalculateReimbursementServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.logging.Level;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "AdminPanelServlet", value = {"/admin-panel", "/admin-panel/update", "/admin-panel/delete-receipt", "/admin-panel/edit-receipt"})
public class AdminPanelServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());
    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();

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

        switch (action) {
            case "/admin-panel/update":
                addNewReimbursement(request);
                response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private void initialize(HttpServletRequest request) {
        request.setAttribute("reimbursement", reimbursementService.getLeastDetails());
        request.setAttribute("reimbursementModificationList", reimbursementService.getAllDetails());
        request.setAttribute("receiptTypes", receiptTypeService.getAllReceiptTypes());
    }

    private void addNewReimbursement(HttpServletRequest request) {
        ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO = new ReimbursementUpdateRequestDTO(
                Double.parseDouble(request.getParameter("perKilometer")),
                Double.parseDouble(request.getParameter("perDay")),
                request.getParameter("enableMileageLimit") != null,
                Double.parseDouble(request.getParameter("mileageLimit")),
                request.getParameter("enableTotalReimbursementLimit") != null,
                Double.parseDouble(request.getParameter("totalReimbursementLimit"))
        );

        reimbursementService.add(reimbursementUpdateRequestDTO);
    }

}
