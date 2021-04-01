package ru.clevertec.check.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.clevertec.check.entities.product.Product;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.MailService;
import ru.clevertec.check.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static ru.clevertec.check.controller.constants.ServletConstants.*;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping("/shopcontroller")
public class ShopController {
    private final ProductService productService;
    private final CheckService checkService;
    private final MailService mailService;

    @GetMapping("")
    @SneakyThrows
    public String selectProducts(HttpServletRequest request, Model model) {
        Map<String, Integer> purchaseParameters = checkService.selectProducts(request);
        checkService.printPDFCheck(checkService.getPDF(purchaseParameters));
        model.addAttribute(ORDER, checkService.getOrder(purchaseParameters));
        model.addAttribute(HTML, checkService.getHTML(purchaseParameters));
        return "pages/check";
    }

    @GetMapping("/mail_send")
    @SneakyThrows
    public String sendMail(String mail, Model model) {
        mailService.sendMailToUser(mail);
        List<Product> products = productService.findAll();
        model.addAttribute(PRODUCTS, products);
        return "pages/shop_page";
    }

    @RequestMapping("/shop")
    @SneakyThrows
    public String sendToShop(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute(PRODUCTS, products);
        return "pages/shop_page";
    }
}
