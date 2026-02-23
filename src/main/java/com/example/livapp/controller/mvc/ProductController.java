package com.example.livapp.controller.mvc;


import com.example.livapp.model.product.Product;
import com.example.livapp.service.abstraction.product.ProductService;
import com.example.livapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(100000);
    }

    // Product List
    @GetMapping("/page/{pageNo}")
    public String findProductsPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<Product> products = new ArrayList<>();
        Page<Product> page = productService.findPaginated(pageNo, pageSize);
        if (page != null) {
            products = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);
        return "products_list";
    }

    @GetMapping("/list")
    public String viewHomePage(Model model) {
        return findProductsPaginated(1, model);
    }


    // Add a new product
    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new_product";
    }


    @PostMapping("/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_product";
        }
        productService.addProduct(product);
        return "redirect:/product/list";
    }

    // Update a product

    @GetMapping("/showProductFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "update_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@Valid Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "update_product";
        }
        productService.addProduct(product);
        return "redirect:/product/list";
    }


}
