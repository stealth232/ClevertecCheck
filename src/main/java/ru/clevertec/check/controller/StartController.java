package ru.clevertec.check.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.entities.product.Product;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.entities.user.UserType;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.UserService;
import ru.clevertec.check.validators.UserValidator;

import javax.validation.Valid;
import java.util.List;

import static ru.clevertec.check.controller.constants.ServletConstants.PRODUCTS;
import static ru.clevertec.check.controller.constants.ServletConstants.USER;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping("")
public class StartController {
    private final ProductService productService;
    private final UserService userService;
    private final UserValidator userValidator;

    @RequestMapping()
    public String showIndexPage() {
        return "index";
    }

    @RequestMapping("/registration")
    public String redirectRegistration() {
        return "pages/registration";
    }

    @RequestMapping("/authorization")
    public String redirectAuthorization() {
        return "pages/authorization";
    }

    @RequestMapping("/admin_access")
    public String adminAccess() {
        return "pages/error/error_admin";
    }

    @ModelAttribute(value = "user")
    public User newUserEntity() {
        return new User();
    }

    @GetMapping("/authRouter")
    public String authorization(@AuthenticationPrincipal User user,
                                Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute(USER, user);
        if (user.getUserType().equals(UserType.USER)) {
            model.addAttribute(PRODUCTS, products);
            return "pages/shop_page";
        }
        return "pages/admin";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(name = "error", required = false)
                                Boolean error, Model model) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", true);
            return "pages/authorization";
        }
        return "pages/authorization";
    }

    @PostMapping("/user_create")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "pages/registration";
        }
        userService.insert(user);
        List<Product> products = productService.findAll();
        model.addAttribute(PRODUCTS, products);
        return "pages/shop_page";
    }
}
