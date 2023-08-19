package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.exception.ReceiptTypeAlreadyInRepositoryException;
import com.tomdud.businesstripapp.exception.ReceiptTypeNotFoundException;
import com.tomdud.businesstripapp.model.ReceiptType;
import com.tomdud.businesstripapp.repository.ReceiptTypeRepository;

import java.util.List;
import java.util.Optional;

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

    public void deleteReceiptTypeByName(String name) throws ReceiptTypeNotFoundException {
        Optional<ReceiptType> optional = receiptTypeRepository.getByName(name);
        if (optional.isEmpty())
            throw new ReceiptTypeNotFoundException(String.format("ReceiptType with name %s not found", name));
        else receiptTypeRepository.delete(name);
    }

    public ReceiptType getReceiptTypeByName(String name) throws ReceiptTypeNotFoundException {
        Optional<ReceiptType> optional = receiptTypeRepository.getByName(name);
        if (optional.isEmpty())
            throw new ReceiptTypeNotFoundException(String.format("ReceiptType with name %s not found", name));
        return optional.get();
    }

    public void saveReceiptType(ReceiptType receiptType) throws ReceiptTypeAlreadyInRepositoryException {
        if (receiptTypeRepository.getByName(receiptType.getName()).isPresent())
            throw new ReceiptTypeAlreadyInRepositoryException(
                    String.format("ReceiptType with name %s already in repository", receiptType.getName())
            );
        else
            receiptTypeRepository.save(receiptType);
    }

}
