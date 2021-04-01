package ru.clevertec.check.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.entities.product.Product;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.UserService;
import ru.clevertec.check.validators.ProductValidator;

import javax.validation.Valid;
import java.util.List;

import static ru.clevertec.check.controller.constants.ServletConstants.PRODUCTS;
import static ru.clevertec.check.controller.constants.ServletConstants.USERS;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    private final ProductValidator productValidator;

    @RequestMapping()
    public String showAuthIndexPage() {
        return "pages/admin";
    }

    @GetMapping("/products")
    public String showProductPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute(PRODUCTS, products);
        return "/pages/products";
    }

    @DeleteMapping("/products/delete/{name}")
    public String deleteProduct(@PathVariable String name) {
        productService.deleteById(name);
        return "redirect:/admin/products";
    }

    @PatchMapping("/products/setpromo/{name}")
    public String editPromo(@PathVariable String name) {
        productService.updateStockToTrue(name);
        return "redirect:/admin/products";
    }

    @PatchMapping("/products/setnopromo/{name}")
    public String editNoPromo(@PathVariable String name) {
        productService.updateStockToFalse(name);
        return "redirect:/admin/products";
    }

    @PatchMapping("/products/setprice/{name}")
    public String setPrice(@RequestParam("cost") double cost,
                           @PathVariable String name) {
        productService.updateCost(name, cost);
        return "redirect:/admin/products";
    }

    @ModelAttribute(value = "product")
    public Product newProductEntity() {
        return new Product();
    }

    @PostMapping("/products/addproduct")
    public String addProduct(@ModelAttribute @Valid Product product,
                             BindingResult bindingResult, Model model) {
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Product> products = productService.findAll();
            model.addAttribute(PRODUCTS, products);
            return "pages/products";
        }
        productService.insert(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/users")
    public String showUsersPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute(USERS, users);
        return "pages/users";
    }

    @DeleteMapping("/users/delete/{login}")
    public String deleteUser(@PathVariable String login, Model model) {
        userService.deleteByLogin(login);
        List<User> users = userService.findAll();
        model.addAttribute(USERS, users);
        return "redirect:/admin/users";
    }

    @PatchMapping("/users/edit/{id}")
    public String changeRole(@PathVariable int id, Model model) {
        userService.updateUserRole(id);
        List<User> users = userService.findAll();
        model.addAttribute(USERS, users);
        return "redirect:/admin/users";
    }
}
