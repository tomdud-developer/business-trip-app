package com.tomdud.businesstripapp.businesstripapp.servlet.admin;

import com.tomdud.businesstripapp.businesstripapp.model.ReceiptType;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptTypeService;
import com.tomdud.businesstripapp.businesstripapp.servlet.calculator.CalculateReimbursementServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ReceiptTypeServlet", value = {"/receipt-type/delete", "/receipt-type/edit", "/receipt-type/update", "/receipt-type/insert"})
public class ReceiptTypeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());
    private final ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "ReceiptTypeServlet::doGet - action - {0}", action);

        switch (action) {
            case "/receipt-type/delete":
                String receiptNameToDelete = request.getParameter("name");
                logger.log(Level.INFO, "ReceiptTypeServlet::doGet - deleting receipt with name {0}", receiptNameToDelete);
                receiptTypeService.deleteReceiptTypeByName(receiptNameToDelete);
                response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
            case "/receipt-type/edit":
                String receiptNameToEdit = request.getParameter("name");
                logger.log(Level.INFO, "ReceiptTypeServlet::doGet - edit receipt with name {0}", receiptNameToEdit);
                ReceiptType receiptType = receiptTypeService.getReceiptTypeByName(receiptNameToEdit);

                request.setAttribute("receiptType", receiptType);
                request.getRequestDispatcher("/receipt-type-edit.jsp").forward(request, response);
                break;
            case "/receipt-type/insert":
                logger.log(Level.INFO, "ReceiptTypeServlet::doGet - inserting new receipt");
                request.setAttribute("receiptType", null);
                request.getRequestDispatcher("/receipt-type-edit.jsp").forward(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        logger.log(Level.INFO, "ReceiptTypeServlet::doPost - action - {0}", action);

        switch (action) {
            case "/receipt-type/update":
                ReceiptType receiptType = receiptTypeService.getReceiptTypeByName(request.getParameter("orgName"));
                receiptType.setName(request.getParameter("name"));
                receiptType.setEnableLimit(request.getParameter("enableLimit") != null);
                receiptType.setLimit(Double.parseDouble(request.getParameter("limit")));

                response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
            case "/receipt-type/insert":
                ReceiptType receiptTypeToSave = new ReceiptType(
                        request.getParameter("name"),
                        request.getParameter("enableLimit") != null,
                        Double.parseDouble(request.getParameter("limit"))
                );
                receiptTypeService.saveReceiptType(receiptTypeToSave);

                response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
        }

    }
}
