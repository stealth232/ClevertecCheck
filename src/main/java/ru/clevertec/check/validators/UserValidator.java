package ru.clevertec.check.validators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.clevertec.check.model.user.User;
import ru.clevertec.check.service.UserService;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class UserValidator implements Validator {
private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "required");
        if(user.getLogin().length()<4 || user.getLogin().length()>32 ){
            errors.rejectValue("login", "Must be longer");
        }
        System.out.println(user);
        if (Objects.nonNull(userService.getUserByLogin(user.getLogin()))) {
            errors.rejectValue("login", "", "User is already exist");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "required");
        if(user.getPassword().length()<4 || user.getPassword().length()>32){
            errors.rejectValue("password", "Must be longer");
        }
    }
}
