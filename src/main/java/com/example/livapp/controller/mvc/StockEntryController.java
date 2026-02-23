package com.example.livapp.controller.mvc;

import com.example.livapp.model.supplier.Supplier;
import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryHolder;
import com.example.livapp.service.abstraction.supplier.SupplierService;
import com.example.livapp.service.abstraction.stock.StockEntryService;
import com.example.livapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockEntryController {

    private final StockEntryService stockEntryService;

    private final SupplierService supplierService;

    @Autowired
    public StockEntryController(StockEntryService stockEntryService, SupplierService supplierService) {
        this.stockEntryService = stockEntryService;
        this.supplierService = supplierService;
    }

    @GetMapping("/page/{pageNo}")
    public String findStockEntriesPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<StockEntry> stockEntries = new ArrayList<>();
        Page<StockEntry> page = stockEntryService.findPaginated(pageNo, pageSize);
        if (page != null) {
            stockEntries = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("stockEntries", stockEntries);
        return "stock_entries_list";
    }

    @GetMapping("/list")
    public String viewHomePage(Model model) {
        return findStockEntriesPaginated(1, model);
    }


    @GetMapping("/showNewStockEntryForm")
    public String showNewPackForm(Model model) {
        StockEntryHolder stockEntryHolder = stockEntryService.getEmptyStockEntryHolder();
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("stockEntryHolder", stockEntryHolder);
        model.addAttribute("suppliers", suppliers);

        return "new_stock_entry";
    }


    @PostMapping("/saveStockEntry")
    public String savePack(@Valid StockEntryHolder stockEntryHolder, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_stock_entry";
        }
        StockEntry stockEntry = stockEntryService.addStockEntry(stockEntryHolder);
        return "redirect:/stock/showStockEntryDetails/" + stockEntry.getId();
    }


    @GetMapping("/showStockEntryDetails/{id}")
    public String showPackDetails(@PathVariable(value = "id") long id, Model model) {
        StockEntryHolder stockEntryHolder = stockEntryService.getStockEntryDetails(id);
        model.addAttribute("stockEntryHolder", stockEntryHolder);
        return "stock_entry_details";
    }


    // Update Stock Entry

    @GetMapping("/showStockEntryFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        List<Supplier> suppliers = supplierService.getSuppliers();
        StockEntryHolder stockEntryHolder = stockEntryService.getStockEntryForUpdate(id);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("stockEntryHolder", stockEntryHolder);
        return "update_stock_entry";
    }

    @PostMapping("/updateStockEntry")
    public String updatePack(@Valid StockEntryHolder stockEntryHolder, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "update_stock_entry";
        }
        stockEntryService.updateStockEntry(stockEntryHolder);
        return "redirect:/stock/showStockEntryDetails/" + stockEntryHolder.getStockEntry().getId();
    }

}
