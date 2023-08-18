package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.entity.Receipt;
import com.tomdud.businesstripapp.businesstripapp.entity.ReceiptType;
import com.tomdud.businesstripapp.businesstripapp.repository.ReceiptTypeRepository;

import java.util.List;
import java.util.function.BiFunction;

public class ReceiptService {

    private static volatile ReceiptService instance;

    private final ReceiptTypeRepository receiptTypeRepository;

    private ReceiptService() {
        receiptTypeRepository = ReceiptTypeRepository.getInstance();
    }

    public static ReceiptService getInstance() {
        ReceiptService result = instance;
        if (result == null) {
            synchronized (ReceiptService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReceiptService();
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

    public double calculateTotalReimbursement(List<Receipt> receipts) {
        return receipts.stream().map(Receipt::getReimbursement).reduce(0.0, Double::sum);
    }
}
