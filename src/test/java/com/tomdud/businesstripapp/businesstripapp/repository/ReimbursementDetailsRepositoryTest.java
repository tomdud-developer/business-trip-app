package com.tomdud.businesstripapp.businesstripapp.repository;

import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class ReimbursementDetailsRepositoryTest {

    ReimbursementDetailsRepository reimbursementDetailsRepository = ReimbursementDetailsRepository.getInstance();

    @Test
    void testDefaultValue() {
        //given
        List<ReimbursementDetails> reimbursementDetailsList = reimbursementDetailsRepository.getAll();

        //then
        Assertions.assertEquals(15.0, reimbursementDetailsList.get(0).getPerDay(), 0.000001);
        Assertions.assertEquals(0.3, reimbursementDetailsList.get(0).getPerKilometer(), 0.000001);
    }

    @Test
    void add() {
        //given
        ReimbursementDetails reimbursementDetails = new ReimbursementDetails(1.0, 100.0, LocalDateTime.now());
        int currentLength = reimbursementDetailsRepository.getAll().size();

        //when
        reimbursementDetailsRepository.add(reimbursementDetails);

        //then
        Assertions.assertEquals(currentLength + 1, reimbursementDetailsRepository.getAll().size());
    }

}