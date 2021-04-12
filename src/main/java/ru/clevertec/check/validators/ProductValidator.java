package ru.clevertec.check.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.clevertec.check.model.product.Product;
import ru.clevertec.check.service.ProductService;

@Component
@AllArgsConstructor
public class ProductValidator implements Validator {
    private final ProductService productService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (productService.getProductByName(product.getName()) != null) {
            errors.rejectValue("name", "", "This product is already exist");
        }
    }
}
