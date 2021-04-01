package ru.clevertec.check.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.service.UserService;

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
        if(userService.getUser(user.getLogin(), user.getPassword()) != null){
            errors.rejectValue("login", "", "User is already exist");
        }
    }
}
