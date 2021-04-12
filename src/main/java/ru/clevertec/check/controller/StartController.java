package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.dto.ResponseData;
import ru.clevertec.check.model.user.User;
import ru.clevertec.check.service.UserService;
import ru.clevertec.check.validators.UserValidator;

import java.util.Optional;

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@RequiredArgsConstructor
public class StartController {
    private final UserService userService;
    private final UserValidator userValidator;

    @PostMapping("/registration")
    public ResponseData<User> save(@RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseData<>(null);
        }
        return new ResponseData<>(userService.save(user));
    }

    @GetMapping("/login")
    public ResponseData<Optional<User>> login(String login, String password) {
        return new ResponseData<>(userService.getUser(login, password));
    }
}
