package com.example.livapp.service.implementation.supplier;

import com.example.livapp.model.supplier.Supplier;
import com.example.livapp.repository.supplier.SupplierRepository;
import com.example.livapp.service.abstraction.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void addSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }


    @Override
    public void deleteSupplier(long supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplier(long supplierId) {
        return supplierRepository.findById(supplierId).orElse(null);
    }
}
