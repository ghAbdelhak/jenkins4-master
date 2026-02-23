package com.example.livapp.service.abstraction.supplier;

import com.example.livapp.model.supplier.Supplier;

import java.util.List;

public interface SupplierService {

    void addSupplier(Supplier supplier);

    void deleteSupplier(long supplierId);

    List<Supplier> getSuppliers();

    Supplier getSupplier(long supplierId);
}
