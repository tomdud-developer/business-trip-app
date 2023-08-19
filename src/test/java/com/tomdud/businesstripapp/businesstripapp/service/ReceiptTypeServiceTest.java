package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.exception.ReceiptTypeAlreadyInRepositoryException;
import com.tomdud.businesstripapp.businesstripapp.exception.ReceiptTypeNotFoundException;
import com.tomdud.businesstripapp.businesstripapp.model.ReceiptType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReceiptTypeServiceTest {

    ReceiptTypeService receiptTypeService = ReceiptTypeService.getInstance();

    @Test
    void getInstance() {
        Assertions.assertEquals(ReceiptTypeService.getInstance(), ReceiptTypeService.getInstance());
    }

    @Test
    void saveReceiptTypeSuccess() {
        //given
        ReceiptType receiptType = new ReceiptType("name1", true, 10.0);
        int currentLength = receiptTypeService.getAllReceiptTypes().size();

        //when
        receiptTypeService.saveReceiptType(receiptType);

        //then
        Assertions.assertTrue(receiptTypeService.getAllReceiptTypes().contains(receiptType));
        Assertions.assertEquals(currentLength + 1, receiptTypeService.getAllReceiptTypes().size());
    }

    @Test
    void saveReceiptTypeWithAlreadyExistingNameThrowException() {
        //given
        ReceiptType receiptType1 = new ReceiptType("name-already-defined-in-repository", true, 10.0);
        ReceiptType receiptType2 = new ReceiptType("name-already-defined-in-repository", false, 0.0);

        //when
        receiptTypeService.saveReceiptType(receiptType1);
        int currentLength = receiptTypeService.getAllReceiptTypes().size();

        //then
        Assertions.assertThrows(
                ReceiptTypeAlreadyInRepositoryException.class,
                () -> receiptTypeService.saveReceiptType(receiptType2)
        );
        Assertions.assertEquals(currentLength, receiptTypeService.getAllReceiptTypes().size());
    }

    @Test
    void deleteReceiptTypeByName() {
        //given
        ReceiptType receiptType = new ReceiptType("name-to-delete", true, 10.0);
        int currentLength = receiptTypeService.getAllReceiptTypes().size();
        receiptTypeService.saveReceiptType(receiptType);

        //when
        Assertions.assertTrue(receiptTypeService.getAllReceiptTypes().contains(receiptType));
        Assertions.assertEquals(currentLength + 1, receiptTypeService.getAllReceiptTypes().size());
        receiptTypeService.deleteReceiptTypeByName("name-to-delete");

        //then
        Assertions.assertFalse(receiptTypeService.getAllReceiptTypes().contains(receiptType));
        Assertions.assertEquals(currentLength, receiptTypeService.getAllReceiptTypes().size());
    }

    @Test
    void deleteReceiptTypeByNameShouldThrowExceptionWhenNameIsNotInRepository() {
        //then
        Assertions.assertThrows(
                ReceiptTypeNotFoundException.class,
                () ->  receiptTypeService.deleteReceiptTypeByName("name-which-is-not-in-repo-to-delete"));
    }

    @Test
    void getReceiptTypeByName() {
        //given
        ReceiptType receiptType = new ReceiptType("name-to-find", true, 10.0);

        //when
        receiptTypeService.saveReceiptType(receiptType);

        //then
        Assertions.assertEquals(receiptType, receiptTypeService.getReceiptTypeByName("name-to-find"));
    }

    @Test
    void getReceiptTypeByNameShouldThrowExceptionIfNameNotExists() {
        //then
        Assertions.assertThrows(
                ReceiptTypeNotFoundException.class,
                () ->  receiptTypeService.getReceiptTypeByName("name-which-is-not-in-repo"));
    }




}