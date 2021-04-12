package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.dto.ResponseData;
import ru.clevertec.check.model.product.Product;
import ru.clevertec.check.model.user.User;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.UserService;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/users")
    public ResponseData<List<User>> getUsers() {
        return new ResponseData<>(userService.findAll());
    }

    @DeleteMapping("/users/{id}")
    public ResponseData<?> deleteUser(@PathVariable Integer id) {
        return new ResponseData<>(generateHttpStatus(userService.deleteUserById(id)));
    }

    @GetMapping("/users/{login}")
    public ResponseData<User> getUserByLogin(@PathVariable String login) {
        return new ResponseData<>(userService.getUserByLogin(login));
    }

    @PatchMapping("/users/{id}")
    public ResponseData<?> changeRole(@PathVariable Integer id) {
        return new ResponseData<>(generateHttpStatus(userService.changeRole(id)));
    }

    @PostMapping("/products")
    public ResponseData<Product> saveProduct(@RequestBody Product product) {
        return new ResponseData<>(productService.save(product));
    }

    @GetMapping("/products")
    public ResponseData<List<Product>> getProducts() {
        return new ResponseData<>(productService.findAll());
    }

    @PatchMapping("/products/{id}")
    public ResponseData<?> changeStock(@PathVariable Integer id) {
        return new ResponseData<>(generateHttpStatus(productService.changeStockById(id)));
    }

    @DeleteMapping("/products/{id}")
    public ResponseData<?> deleteProduct(@PathVariable Integer id) {
        return new ResponseData<>(generateHttpStatus(productService.deleteProductById(id)));
    }

    @PutMapping("/products/cost/{id}")
    public ResponseData<?> setCost(@RequestParam("cost") double cost,
                                   @PathVariable Integer id) {
        return new ResponseData<>(generateHttpStatus(productService.updateCost(cost, id)));
    }

    private HttpStatus generateHttpStatus(Integer result) {
        if (result > 0) return HttpStatus.OK;
        else return HttpStatus.NOT_MODIFIED;
    }
}
