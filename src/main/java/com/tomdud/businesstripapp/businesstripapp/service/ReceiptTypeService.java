package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.model.ReceiptType;
import com.tomdud.businesstripapp.businesstripapp.repository.ReceiptTypeRepository;

import java.util.List;

public class ReceiptTypeService {

    private static volatile ReceiptTypeService instance;

    private final ReceiptTypeRepository receiptTypeRepository;

    private ReceiptTypeService() {
        receiptTypeRepository = ReceiptTypeRepository.getInstance();
    }

    public static ReceiptTypeService getInstance() {
        ReceiptTypeService result = instance;
        if (result == null) {
            synchronized (ReceiptTypeService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReceiptTypeService();
                }
            }
        }
        return result;
    }

    public List<ReceiptType> getAllReceiptTypes() {
        return receiptTypeRepository.getAll();
    }

    public void deleteReceiptTypeByName(String name) {
        receiptTypeRepository.delete(name);
    }

    public ReceiptType getReceiptTypeByName(String name) {
        return receiptTypeRepository.getByName(name);
    }

    public void saveReceiptType(ReceiptType receiptType) {
        receiptTypeRepository.save(receiptType);
    }

}
