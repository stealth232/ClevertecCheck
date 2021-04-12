package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.check.service.CheckService;

import javax.servlet.http.HttpServletRequest;

@RestController
@EnableAutoConfiguration
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final CheckService checkService;

    @GetMapping("")
    @SneakyThrows
    public StringBuilder selectProducts(HttpServletRequest request) {
        return checkService.getTXT(checkService.selectProducts(request));
    }
}
