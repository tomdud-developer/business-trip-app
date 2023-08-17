package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.entity.ReceiptType;
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

    public List<ReceiptType> getAll() {
        return receiptTypeRepository.getAll();
    }

    public void deleteByName(String name) {
        receiptTypeRepository.delete(name);
    }

    public ReceiptType getByName(String name) {
        return receiptTypeRepository.getByName(name);
    }

    public void save(ReceiptType receiptType) {
        receiptTypeRepository.save(receiptType);
    }
}
