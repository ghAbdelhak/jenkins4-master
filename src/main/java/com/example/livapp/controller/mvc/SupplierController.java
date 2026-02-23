package com.example.livapp.controller.mvc;


import com.example.livapp.model.supplier.Supplier;
import com.example.livapp.service.abstraction.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }


    @GetMapping("/list")
    public String findSupplier(Model model) {
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "suppliers_list";
    }


    @GetMapping("/showNewSupplierForm")
    public String showNewSupplierForm(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        return "new_supplier";
    }


    @PostMapping("/saveSupplier")
    public String saveSupplier(@Valid Supplier supplier, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_supplier";
        }
        supplierService.addSupplier(supplier);
        return "redirect:/supplier/list";
    }


    @GetMapping("/showSupplierFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Supplier supplier = supplierService.getSupplier(id);
        model.addAttribute("supplier", supplier);
        return "update_supplier";
    }

    @PostMapping("/updateSupplier")
    public String updateSupplier(@Valid Supplier supplier, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "update_supplier";
        }
        supplierService.addSupplier(supplier);
        return "redirect:/supplier/list";
    }


    @GetMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable(value = "id") long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/supplier/list";
    }

}
