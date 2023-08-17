package com.tomdud.businesstripapp.businesstripapp.servlet;

import com.tomdud.businesstripapp.businesstripapp.entity.ReceiptType;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptTypeService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ReceiptTypeServlet", value = {"/receipt-type/delete", "/receipt-type/edit"})
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
                receiptTypeService.deleteByName(receiptNameToDelete);
                response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
            case "/receipt-type/edit":
                String receiptNameToEdit = request.getParameter("name");
                logger.log(Level.INFO, "ReceiptTypeServlet::doGet - edit receipt with name {0}", receiptNameToEdit);
                ReceiptType receiptType = receiptTypeService.getByName(receiptNameToEdit);

                request.setAttribute("receiptType", receiptType);
                request.getRequestDispatcher("/receipt-type-edit.jsp").forward(request, response);

                //response.sendRedirect(request.getContextPath() + "/admin-panel");
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
