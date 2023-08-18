package com.tomdud.businesstripapp.businesstripapp.repository;

import com.tomdud.businesstripapp.businesstripapp.model.Reimbursement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class ReimbursementRepositoryTest {

    ReimbursementRepository reimbursementRepository = ReimbursementRepository.getInstance();

    @Test
    void testDefaultValue() {
        //given
        List<Reimbursement> reimbursementList = reimbursementRepository.getAll();

        //then
        Assertions.assertEquals(1, reimbursementList.size());
        Assertions.assertEquals(15.0, reimbursementList.get(0).getPerDay(), 0.000001);
        Assertions.assertEquals(0.3, reimbursementList.get(0).getPerKilometer(), 0.000001);
    }

    @Test
    void add() {
        //given
        Reimbursement reimbursement = new Reimbursement(1.0, 100.0, LocalDateTime.now());

        //when
        reimbursementRepository.add(reimbursement);

        //then
        Assertions.assertEquals(2, reimbursementRepository.getAll().size());
    }

}