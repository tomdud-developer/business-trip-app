package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.CarUsage;
import com.tomdud.businesstripapp.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.businesstripapp.model.TotalReimbursement;
import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;
import com.tomdud.businesstripapp.businesstripapp.service.DaysAllowanceService;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "CalculateReimbursementServlet",
        value = {
                "/calculate-reimbursement",
                "/calculate-reimbursement/modify-duration",
                "/calculate-reimbursement/modify-car-usage",
                "/calculate-reimbursement/addDisabledDay",
                "/calculate-reimbursement/deleteDisabledDay"
        }
)
public class CalculateReimbursementServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReceiptService receiptService = ReceiptService.getInstance();
    private final DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.log(Level.INFO, "Calculator panel get");

        request.setAttribute("receiptTypes", receiptService.getAllReceiptTypes());

        List<Receipt> receipts = (List<Receipt>) request.getSession().getAttribute("receiptList");
        if(receipts == null) {
            receipts = new ArrayList<>();
            request.getSession().setAttribute("receiptList", receipts);
        }

        TripDuration tripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
        if(tripDuration == null) {
            tripDuration = new TripDuration(LocalDate.now(), LocalDate .now(), 1, new HashSet<>());
            request.getSession().setAttribute("tripDuration", tripDuration);
        }

        CarUsage carUsage = (CarUsage) request.getSession().getAttribute("carUsage");
        if(carUsage == null) {
            carUsage = new CarUsage(0.0, reimbursementService.getLeast());
            request.getSession().setAttribute("carUsage", carUsage);
        }

        request.getSession().setAttribute("totalAllowance", daysAllowanceService.calculateTotalAllowance(tripDuration, reimbursementService.getLeast()));
        request.getSession().setAttribute("expensesTotalReimbursement", receiptService.calculateTotalReimbursement(receipts));

        TotalReimbursement totalReimbursement = new TotalReimbursement(receipts, carUsage, tripDuration);
        request.getSession().setAttribute("totalReimbursement", reimbursementService.calculateTotalReimbursement(totalReimbursement));

        request.getRequestDispatcher("reimbursement-calculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        logger.log(Level.INFO, "Calculator panel post - action - {0}", action);

        switch (action) {
            case "/calculate-reimbursement/modify-duration":
            case "calculate-reimbursement/modify-duration":
                logger.log(Level.INFO, "CalculatorPanel::doPost - change in trip duration form - changed field: {0}",
                        request.getParameter("tripFieldChanged"));
                TripDuration tripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
                switch (request.getParameter("tripFieldChanged")) {
                    case "numberOfDays":
                        tripDuration = reimbursementService.getNewTripDurationBasedOnChangedDays(
                            tripDuration,
                            Integer.parseInt(request.getParameter("numberOfDays"))
                        );
                        request.getSession().setAttribute("tripDuration", tripDuration);
                        break;
                    case "tripEndDate":
                        tripDuration = reimbursementService.getNewTripDurationBasedOnChangedEndDate(
                                tripDuration,
                                LocalDate.parse(request.getParameter("tripEndDate"))
                        );
                        request.getSession().setAttribute("tripDuration", tripDuration);
                        break;
                    case "tripStartDate":
                        tripDuration = reimbursementService.getNewTripDurationBasedOnChangedStartDate(
                                tripDuration,
                                LocalDate.parse(request.getParameter("tripStartDate"))
                        );
                        request.getSession().setAttribute("tripDuration", tripDuration);
                        break;
                }

                request.getSession().setAttribute("tripDuration", tripDuration);
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;
            case "/calculate-reimbursement/addDisabledDay":
            case "calculate-reimbursement/addDisabledDay":
                TripDuration currentTripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
                LocalDate dateToDisable = LocalDate.parse(request.getParameter("disabledDayDate"));
                if (daysAllowanceService.isDateBetween(
                        dateToDisable,
                        currentTripDuration.getStartDate(),
                        currentTripDuration.getEndDate())
                ) currentTripDuration.getDisabledDays().add(dateToDisable);

                request.getSession().setAttribute("tripDuration", currentTripDuration);
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;

            case "/calculate-reimbursement/deleteDisabledDay":
            case "calculate-reimbursement/deleteDisabledDay":
                TripDuration currentTripDuration2 = (TripDuration) request.getSession().getAttribute("tripDuration");
                LocalDate disabledDateToDelete = LocalDate.parse(request.getParameter("disabledDayDateToDelete"));
                currentTripDuration2.getDisabledDays().remove(disabledDateToDelete);

                request.getSession().setAttribute("tripDuration", currentTripDuration2);
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;
            case "/calculate-reimbursement/modify-car-usage":
            case "calculate-reimbursement/modify-car-usage":
                double distance = Double.parseDouble(request.getParameter("distance"));
                CarUsage newCarUsage= new CarUsage(distance, reimbursementService.getLeast());
                request.getSession().setAttribute("carUsage", newCarUsage);
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
                break;
        }


    }



}
