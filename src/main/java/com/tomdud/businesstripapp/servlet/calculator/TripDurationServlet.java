package com.tomdud.businesstripapp.servlet.calculator;

import com.tomdud.businesstripapp.exception.FormValueNotCorrectException;
import com.tomdud.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.model.TripDuration;
import com.tomdud.businesstripapp.service.DaysAllowanceService;
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

    private static final Logger logger = Logger.getLogger(TripDurationServlet.class.getName());
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

        try {
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
        } catch (Exception exception) {
            response.sendRedirect(request.getContextPath() + "/calculate-reimbursement?error=true&errorMessage=" + exception.getMessage());
            return;
        }
        response.sendRedirect(request.getContextPath() + "/calculate-reimbursement");
    }

    private void modifyDuration(HttpServletRequest request) {
        String tripFieldChanged = request.getParameter("tripFieldChanged");
        logger.log(Level.INFO,
                "TripDurationServlet::modify-duration change in trip duration form - changed field: {0}",
                tripFieldChanged);
        TripDuration tripDuration = retrieveTripDurationFromSession(request);

        switch (tripFieldChanged) {
            case "numberOfDays":
                String numberOfDaysString = request.getParameter("numberOfDays");
                if (numberOfDaysString == null || numberOfDaysString.length() == 0)
                    throw new FormValueNotCorrectException("Provide value number of days");

                daysAllowanceService.modifyTripDurationBasedOnChangedDays(
                        tripDuration,
                        Integer.parseInt(numberOfDaysString)
                );
                break;
            case "tripEndDate":
                daysAllowanceService.modifyTripDurationBasedOnChangedEndDate(
                        tripDuration,
                        LocalDate.parse(request.getParameter("tripEndDate"))
                );
                break;
            case "tripStartDate":
                daysAllowanceService.modifyTripDurationBasedOnChangedStartDate(
                        tripDuration,
                        LocalDate.parse(request.getParameter("tripStartDate"))
                );
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tripFieldChanged);
        }
    }

    private TripDuration retrieveTripDurationFromSession(HttpServletRequest request) {
        return ((ReimbursementSummary) request.getSession().getAttribute("reimbursementSummary")).getTripDuration();
    }

    private void addDisabledDay(HttpServletRequest request) {
        TripDuration currentTripDuration = retrieveTripDurationFromSession(request);
        LocalDate dateToDisable = LocalDate.parse(request.getParameter("disabledDayDate"));
        if (daysAllowanceService.isDateBetweenOrEquals(
                dateToDisable,
                currentTripDuration.getStartDate(),
                currentTripDuration.getEndDate())
        ) currentTripDuration.getDisabledDays().add(dateToDisable);
        else throw new FormValueNotCorrectException("Date to disable out of start date and end date");
    }

    private void deleteDisabledDay(HttpServletRequest request) {
        TripDuration currentTripDuration = retrieveTripDurationFromSession(request);
        LocalDate disabledDateToDelete = LocalDate.parse(request.getParameter("disabledDayDateToDelete"));
        currentTripDuration.getDisabledDays().remove(disabledDateToDelete);
    }


}
