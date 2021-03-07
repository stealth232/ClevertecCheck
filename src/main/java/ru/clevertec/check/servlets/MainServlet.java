package ru.clevertec.check.servlets;

import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.parameters.ProductParameters;
import ru.clevertec.check.entities.product.Order;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.service.Check;
import ru.clevertec.check.service.impl.CheckImpl;
import ru.clevertec.check.utils.creator.OrderCreator;
import ru.clevertec.check.utils.creator.impl.OrderCreatorImpl;
import ru.clevertec.check.utils.parser.ArgsParser;
import ru.clevertec.check.utils.parser.impl.ArgsParserImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static ru.clevertec.check.service.CheckConstants.*;

@WebServlet(urlPatterns = SHOP)
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType(TEXT_HTML);
        String[] itemId = req.getParameterValues("itemId");
        String[] quantity = req.getParameterValues("quantity");
        String card = req.getParameter(CARD);
        String[] resultCheck = new String[itemId.length + 1];
        for (int i = 0; i < itemId.length; i++) {
            if (itemId[i].isEmpty() || quantity[i].isEmpty()) {
                itemId[i] = ZERO;
                quantity[i] = ZERO;
            }
            resultCheck[i] = itemId[i] + URL_DELIMETER + quantity[i];
            if (!card.isEmpty()) {
                resultCheck[itemId.length] = URL_CARD + card;
            } else if (card.isEmpty()) {
                resultCheck[itemId.length] = URL_CARD + ZERO;
            }
        }
        try {
            List<ProductParameters> products = Repository.getProductList();
            ArgsParser argsParser = new ArgsParserImpl();
            OrderCreator orderCreator = new OrderCreatorImpl();
            List<String> list = argsParser.parsParams(resultCheck);
            Map<String, Integer> map = orderCreator.makeOrder(list);
            Check check = new CheckImpl(map);
            StringBuilder html = check.htmlCheck(products);
            Order order = check.getOrder(products);////
            req.setAttribute("html", html);
            req.setAttribute("list", order.getProducts());
            req.setAttribute("order", order);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(SHOP + CHECK);
            requestDispatcher.forward(req, resp);
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }
}
