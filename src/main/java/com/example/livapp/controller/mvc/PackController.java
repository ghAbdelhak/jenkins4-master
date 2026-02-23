package com.example.livapp.controller.mvc;


import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackHolder;
import com.example.livapp.service.abstraction.pack.PackService;
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
@RequestMapping("/pack")
public class PackController {

    private final PackService packService;

    @Autowired
    public PackController(PackService packService) {
        this.packService = packService;
    }


    // Pack List
    @GetMapping("/page/{pageNo}")
    public String findPacksPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<Pack> packs = new ArrayList<>();
        Page<Pack> page = packService.findPaginated(pageNo, pageSize);
        if (page != null) {
            packs = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("packs", packs);
        return "packs_list";
    }

    @GetMapping("/list")
    public String viewHomePage(Model model) {
        return findPacksPaginated(1, model);
    }


    @GetMapping("/showNewPackForm")
    public String showNewPackForm(Model model) {
        PackHolder packHolder = packService.getEmptyPackHolder();
        model.addAttribute("packHolder", packHolder);
        return "new_pack";
    }


    @PostMapping("/savePack")
    public String savePack(@Valid PackHolder packHolder, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_pack";
        }
        Pack pack = packService.addPackHolder(packHolder);
        return "redirect:/pack/showPackDetails/" + pack.getId();
    }


    @GetMapping("/showPackDetails/{id}")
    public String showPackDetails(@PathVariable(value = "id") long id, Model model) {
        PackHolder packHolder = packService.getPackDetails(id);
        model.addAttribute("packHolder", packHolder);
        return "pack_details";
    }


    // Update a pack

    @GetMapping("/showPackFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        PackHolder packHolder = packService.getPackHolder(id);
        model.addAttribute("packHolder", packHolder);
        return "update_pack";
    }

    @PostMapping("/updatePack")
    public String updatePack(@Valid PackHolder packHolder, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "update_pack";
        }
        packService.updatePackHolder(packHolder);
        return "redirect:/pack/showPackDetails/" + packHolder.getPack().getId();
    }


}
