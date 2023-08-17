package com.tomdud.businesstripapp.businesstripapp.servlet;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.entity.Reimbursement;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.logging.Level;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "AdminPanelServlet", value = {"/admin-panel", "/admin-panel/update"})
public class AdminPanelServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());
    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("reimbursement", reimbursementService.getLeast());
        request.setAttribute("reimbursementModificationList", reimbursementService.getAll());
        request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "Admin panel post - action - " + action);

        switch (action) {
            case "/admin-panel/update":
                ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO = new ReimbursementUpdateRequestDTO(
                        Double.parseDouble(request.getParameter("perKilometer")),
                        Double.parseDouble(request.getParameter("perDay"))
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
