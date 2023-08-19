package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.model.ReimbursementUpdateRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReimbursementDetailsServiceTest {


    ReimbursementDetailsService reimbursementDetailsService = ReimbursementDetailsService.getInstance();

    @Test
    void getInstance() {
        Assertions.assertEquals(ReimbursementDetailsService.getInstance(), ReimbursementDetailsService.getInstance());
    }

    @Test
    void add() {
        //given
        ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO = new ReimbursementUpdateRequestDTO(
                10.0, 15.0, true, 200.0, true, 1000.0
        );
        int currentSize = reimbursementDetailsService.getAllDetails().size();

        //when
        reimbursementDetailsService.add(reimbursementUpdateRequestDTO);

        //then
        Assertions.assertEquals(currentSize + 1, reimbursementDetailsService.getAllDetails().size());
    }

    @Test
    void getLeastDetails() {
        //given
        ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO = new ReimbursementUpdateRequestDTO(
                1.0, 15.5, false, 0.0, true, 50000.0
        );
        int currentSize = reimbursementDetailsService.getAllDetails().size();

        //when
        reimbursementDetailsService.add(reimbursementUpdateRequestDTO);

        //then
        Assertions.assertEquals(1.0, reimbursementDetailsService.getLeastDetails().getPerKilometer());
        Assertions.assertEquals(15.5, reimbursementDetailsService.getLeastDetails().getPerDay());
        Assertions.assertFalse(reimbursementDetailsService.getLeastDetails().isEnableMileageLimit());
        Assertions.assertEquals(0.0, reimbursementDetailsService.getLeastDetails().getMileageLimit());
        Assertions.assertTrue(reimbursementDetailsService.getLeastDetails().isEnableTotalReimbursementLimit());
        Assertions.assertEquals(50000.0, reimbursementDetailsService.getLeastDetails().getTotalReimbursementLimit());
    }
}