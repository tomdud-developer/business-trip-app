package com.tomdud.businesstripapp.businesstripapp.repository;

import com.tomdud.businesstripapp.businesstripapp.entity.ReceiptType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReceiptTypeRepository {

    private static volatile ReceiptTypeRepository instance;

    private Set<ReceiptType> repository;

    private ReceiptTypeRepository() {
        repository = new HashSet<>();
        repository.add(new ReceiptType("taxi", true, 20.0));
        repository.add(new ReceiptType("hotel", false, 0.0));
        repository.add(new ReceiptType("plane ticket", false, 0.0));
        repository.add(new ReceiptType("train", false, 0.0));
    }

    public static ReceiptTypeRepository getInstance() {
        ReceiptTypeRepository result = instance;
        if (result == null) {
            synchronized (ReceiptTypeRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReceiptTypeRepository();
                }
            }
        }
        return result;
    }

    public void add(ReceiptType receiptType) {
        repository.add(receiptType);
    }

    public List<ReceiptType> getAll() {
        return new ArrayList<>(repository);
    }

    public void delete(String name) {
        repository.removeIf(type -> type.getName().equals(name));
    }

    public ReceiptType getByName(String name) {
        return repository
                .stream()
                .filter(type -> type.getName().equals(name))
                .limit(1)
                .collect(Collectors.toList())
                .get(0);
    }

    public void save(ReceiptType receiptType) {
        repository.add(receiptType);
    }
}
