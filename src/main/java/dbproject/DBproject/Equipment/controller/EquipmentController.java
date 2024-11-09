package dbproject.DBproject.Equipment.controller;

import dbproject.DBproject.Equipment.domain.Equipment;
import dbproject.DBproject.Equipment.service.EquipmentCategoryService;
import dbproject.DBproject.Equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentCategoryService equipmentCategoryService;

    @GetMapping
    public String getAllEquipment(Model model) {
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());
        model.addAttribute("categories", equipmentCategoryService.getAllEquipmentCategories());
        model.addAttribute("newEquipment", new Equipment());
        return "equipment";
    }

    @PostMapping("/add")
    public String addEquipment(@ModelAttribute Equipment newEquipment) {
        equipmentService.addEquipment(newEquipment);
        return "redirect:/equipment";
    }

    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable("id") Integer id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/equipment";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        model.addAttribute("equipment", equipment);
        model.addAttribute("categories", equipmentCategoryService.getAllEquipmentCategories());
        return "equipmentEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editEquipment(@PathVariable("id") Integer id, @ModelAttribute Equipment equipment) {
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/equipment";
    }
}
