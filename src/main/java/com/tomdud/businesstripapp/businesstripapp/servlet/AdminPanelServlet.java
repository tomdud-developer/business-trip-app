package com.tomdud.businesstripapp.businesstripapp.servlet;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptService;
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
    private final ReceiptService receiptService = ReceiptService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "Admin panel get - action - {}", action);

        request.setAttribute("reimbursement", reimbursementService.getLeast());
        request.setAttribute("reimbursementModificationList", reimbursementService.getAll());
        request.setAttribute("receiptTypes", receiptService.getAllReceiptTypes());

        request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "Admin panel post - action - {0}", action);

        switch (action) {
            case "/admin-panel/update":
                ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO = new ReimbursementUpdateRequestDTO(
                        Double.parseDouble(request.getParameter("perKilometer")),
                        Double.parseDouble(request.getParameter("perDay")),
                        request.getParameter("enableMileageLimit") != null,
                        Double.parseDouble(request.getParameter("mileageLimit")),
                        request.getParameter("enableTotalReimbursementLimit") != null,
                        Double.parseDouble(request.getParameter("totalReimbursementLimit"))
                );

                reimbursementService.add(reimbursementUpdateRequestDTO);
                response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
            default:
                request.setAttribute("reimbursement", reimbursementService.getLeast());
                doGet(request, response);
                break;
        }
    }

}
