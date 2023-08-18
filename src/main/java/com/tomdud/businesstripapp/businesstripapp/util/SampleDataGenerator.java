package com.tomdud.businesstripapp.businesstripapp.util;

import com.tomdud.businesstripapp.businesstripapp.model.*;
import com.tomdud.businesstripapp.businesstripapp.service.ReceiptService;
import com.tomdud.businesstripapp.businesstripapp.service.ReimbursementService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SampleDataGenerator {

    private final ReimbursementService reimbursementService = ReimbursementService.getInstance();
    private final ReceiptService receiptService = ReceiptService.getInstance();

    public ReimbursementSummary getReimbursementSummary() {
        return reimbursementService.calculateTotalReimbursement(
                getTripDuration(),
                getReceiptList(),
                getCarUsage()
        );
    }

    public TripDuration getTripDuration() {
        Random random = new Random();
        int randomDays = random.nextInt(100);

        return new TripDuration(
                LocalDate.now(),
                LocalDate.now().plusDays(randomDays),
                randomDays + 1,
                randomDisabledDatesBetween(LocalDate.now(), LocalDate.now().plusDays(randomDays))
        );
    }

    private Set<LocalDate> randomDisabledDatesBetween(LocalDate localDate1, LocalDate localDate2) {
        Random random = new Random();
        int daysBetween = (int) ChronoUnit.DAYS.between(localDate1, localDate2);

        Set<LocalDate> localDates = new HashSet<>();

        for (int i = 0; i < random.nextInt(daysBetween); i++) {
            localDates.add(localDate1.plusDays(random.nextInt(daysBetween)));
        }

        return localDates;
    }


    public List<Receipt> getReceiptList() {
        Random random = new Random();

        List<Receipt> list = new ArrayList<>();
        for (int i = 0; i < random.nextInt(10); i++) {
            list.add(getReceipt());
        }

        return list;
    }

    public Receipt getReceipt() {
        Random random = new Random();
        int receiptTypesNumber = receiptService.getAllReceiptTypes().size();

        return new Receipt(
                random.nextDouble() * random.nextInt(200),
                receiptService.getAllReceiptTypes().get(random.nextInt(receiptTypesNumber))
        );
    }

    public CarUsage getCarUsage() {
        Random random = new Random();

        return new CarUsage(
                random.nextDouble() * random.nextInt(500),
                reimbursementService.getLeast()
        );
    }



}
