package com.tomdud.businesstripapp.businesstripapp.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "CalculateReimbursementServlet", value = "/calculate-reimbursement")
public class CalculateReimbursementServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Receive post request");
    }



}
