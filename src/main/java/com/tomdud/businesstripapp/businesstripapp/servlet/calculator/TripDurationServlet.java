package com.tomdud.businesstripapp.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;
import com.tomdud.businesstripapp.businesstripapp.service.DaysAllowanceService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "TripDurationServlet",
        value = {
            "/calculate-reimbursement/modify-duration",
            "/calculate-reimbursement/addDisabledDay",
            "/calculate-reimbursement/deleteDisabledDay"
        }
)
public class TripDurationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CalculateReimbursementServlet.class.getName());
    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "TripDurationServlet::doGet - redirect to calculator");
        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        logger.log(Level.INFO, "TripDurationServlet::doPost - action - {0}", action);

        switch (action) {
            case "/calculate-reimbursement/modify-duration":
                modifyDuration(request);
                break;
            case "/calculate-reimbursement/addDisabledDay":
                addDisabledDay(request);
                break;
            case "/calculate-reimbursement/deleteDisabledDay":
                deleteDisabledDay(request);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    private void modifyDuration(HttpServletRequest request) {
        String tripFieldChanged = request.getParameter("tripFieldChanged");
        logger.log(Level.INFO,
                "TripDurationServlet::modify-duration change in trip duration form - changed field: {0}",
                tripFieldChanged);
        TripDuration tripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");

        switch (tripFieldChanged) {
            case "numberOfDays":
                tripDuration = reimbursementService.getNewTripDurationBasedOnChangedDays(
                        tripDuration,
                        Integer.parseInt(request.getParameter("numberOfDays"))
                );
                break;
            case "tripEndDate":
                tripDuration = reimbursementService.getNewTripDurationBasedOnChangedEndDate(
                        tripDuration,
                        LocalDate.parse(request.getParameter("tripEndDate"))
                );
                break;
            case "tripStartDate":
                tripDuration = reimbursementService.getNewTripDurationBasedOnChangedStartDate(
                        tripDuration,
                        LocalDate.parse(request.getParameter("tripStartDate"))
                );
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tripFieldChanged);
        }
        request.getSession().setAttribute("tripDuration", tripDuration);
    }

    private void addDisabledDay(HttpServletRequest request) {
        TripDuration currentTripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
        LocalDate dateToDisable = LocalDate.parse(request.getParameter("disabledDayDate"));
        if (daysAllowanceService.isDateBetween(
                dateToDisable,
                currentTripDuration.getStartDate(),
                currentTripDuration.getEndDate())
        ) currentTripDuration.getDisabledDays().add(dateToDisable);
    }

    private void deleteDisabledDay(HttpServletRequest request) {
        TripDuration currentTripDuration = (TripDuration) request.getSession().getAttribute("tripDuration");
        LocalDate disabledDateToDelete = LocalDate.parse(request.getParameter("disabledDayDateToDelete"));
        currentTripDuration.getDisabledDays().remove(disabledDateToDelete);
    }
}
