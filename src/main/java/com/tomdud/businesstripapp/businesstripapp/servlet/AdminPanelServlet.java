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
        String action = request.getServletPath();

        logger.log(Level.INFO, "Admin panel - action - " + action);

        switch (action) {
            case "/new":
                break;
            default:
                request.setAttribute("reimbursement", reimbursementService.getLeast());
                request.getRequestDispatcher("admin-panel.jsp").forward(request, response);
                break;
        }

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
                response.sendRedirect("admin-panel");
                break;
            default:
                request.setAttribute("reimbursement", reimbursementService.getLeast());
                response.sendRedirect("admin-panel");
                break;
        }
    }

}
