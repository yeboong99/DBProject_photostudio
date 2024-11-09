package dbproject.DBproject.Customers.controller;

import dbproject.DBproject.Customers.domain.Customers;
import dbproject.DBproject.Customers.service.CustomersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GetMapping
    public String listCustomers(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("customers", customersService.findByNameContainingIgnoreCase(name));
        } else {
            model.addAttribute("customers", customersService.getAllCustomers());
        }
        return "customers";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customers", new Customers());
        return "create_customers";
    }

    @PostMapping
    public String createCustomer(@Valid @ModelAttribute Customers customers, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_customers";
        }
        customersService.saveCustomers(customers);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customersService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Customers customers = customersService.findById(id);
        model.addAttribute("customers", customers);
        return "edit_customers";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @Valid @ModelAttribute Customers updatedCustomer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_customers";
        }
        customersService.updateCustomer(id, updatedCustomer);
        return "redirect:/customers";
    }


}
