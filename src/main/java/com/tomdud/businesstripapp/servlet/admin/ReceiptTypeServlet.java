package com.tomdud.businesstripapp.servlet.admin;

import com.tomdud.businesstripapp.exception.FormValueNotCorrectException;
import com.tomdud.businesstripapp.model.ReceiptType;
import com.tomdud.businesstripapp.service.ReceiptTypeService;
import com.tomdud.businesstripapp.servlet.calculator.CalculateReimbursementServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ReceiptTypeServlet", value = {"/receipt-type-delete", "/receipt-type-edit", "/receipt-type-update", "/receipt-type-insert"})
public class ReceiptTypeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());
    private final ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "ReceiptTypeServlet::doGet - action - {0}", action);

        try {
            switch (action) {
                case "/receipt-type-delete":
                    if (request.getParameter("name").length() == 0)
                        throw new FormValueNotCorrectException("Provide correct receipt name");

                    String receiptNameToDelete = request.getParameter("name");
                    logger.log(Level.INFO, "ReceiptTypeServlet::doGet - deleting receipt with name {0}", receiptNameToDelete);
                    receiptTypeService.deleteReceiptTypeByName(receiptNameToDelete);
                    response.sendRedirect(request.getContextPath() + "/admin-panel");
                    break;
                case "/receipt-type-edit":
                    if (request.getParameter("name").length() == 0)
                        throw new FormValueNotCorrectException("Provide correct receipt name");

                    String receiptNameToEdit = request.getParameter("name");
                    logger.log(Level.INFO, "ReceiptTypeServlet::doGet - edit receipt with name {0}", receiptNameToEdit);
                    ReceiptType receiptType = receiptTypeService.getReceiptTypeByName(receiptNameToEdit);

                    request.setAttribute("receiptType", receiptType);
                    request.getRequestDispatcher("/receipt-type-edit.jsp").forward(request, response);
                    break;
                case "/receipt-type-insert":
                    logger.log(Level.INFO, "ReceiptTypeServlet::doGet - inserting new receipt");
                    request.setAttribute("receiptType", null);
                    request.getRequestDispatcher("/receipt-type-edit.jsp").forward(request, response);
                    break;
            }
        } catch (Exception exception) {
            response.sendRedirect(request.getContextPath() + "/admin-panel?error=true&errorMessage=" + exception.getMessage());
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        logger.log(Level.INFO, "ReceiptTypeServlet::doPost - action - {0}", action);

        try {
            switch (action) {
                case "/receipt-type-update":
                    ReceiptType receiptType = receiptTypeService.getReceiptTypeByName(request.getParameter("orgName"));

                    if (request.getParameter("name").length() == 0)
                        throw new FormValueNotCorrectException("Provide correct receipt name");

                    if (request.getParameter("limit").length() == 0)
                        throw new FormValueNotCorrectException("Provide correct receipt limit value");

                    receiptType.setName(request.getParameter("name"));
                    receiptType.setEnableLimit(request.getParameter("enableLimit") != null);
                    receiptType.setLimit(Double.parseDouble(request.getParameter("limit")));

                    response.sendRedirect(request.getContextPath() + "/admin-panel");
                    break;
                case "/receipt-type-insert":
                    if (request.getParameter("name").length() == 0)
                        throw new FormValueNotCorrectException("Provide correct receipt name");

                    if (request.getParameter("limit").length() == 0)
                        throw new FormValueNotCorrectException("Provide correct receipt limit value");

                    ReceiptType receiptTypeToSave = new ReceiptType(
                            request.getParameter("name"),
                            request.getParameter("enableLimit") != null,
                            Double.parseDouble(request.getParameter("limit"))
                    );
                    receiptTypeService.saveReceiptType(receiptTypeToSave);

                    response.sendRedirect(request.getContextPath() + "/admin-panel");
                    break;
            }
        } catch (Exception exception) {
            response.sendRedirect(request.getContextPath() + "/admin-panel?error=true&errorMessage=" + exception.getMessage());
            return;
        }

    }
}
