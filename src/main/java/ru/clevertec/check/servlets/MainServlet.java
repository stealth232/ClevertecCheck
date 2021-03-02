package ru.clevertec.check.servlets;

import ru.clevertec.check.utils.creator.impl.OrderCreatorImpl;
import ru.clevertec.check.dao.DBController;
import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.service.impl.CheckImpl;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.utils.mylinkedlist.MyLinkedList;
import ru.clevertec.check.entities.parameters.ProductParameters;
import ru.clevertec.check.utils.parser.impl.ArgsParserImpl;
import ru.clevertec.check.utils.proxy.ProxyFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/hello")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String[] itemId = req.getParameterValues("itemId");
        String[] quantity = req.getParameterValues("quantity");
        String card = req.getParameter("card");
        String[] resultCheck = new String[itemId.length + 1];

        for (int i = 0; i < itemId.length; i++) {
            resultCheck[i] = itemId[i] + "-" + quantity[i];
            if (!card.isBlank()) {
                resultCheck[itemId.length] = "card-" + card;
            } else if (card.isBlank()) {
                resultCheck[itemId.length] = null;
            }
        }

        Repository repository = Repository.getInstance();
        repository.removeTable();
        repository.createTable();
        repository.fillRepository();

        List<ProductParameters> products = new MyLinkedList<>();
        List<ProductParameters> productsProxy = (List<ProductParameters>) ProxyFactory.doProxy(products);
        for (int i = 1; i < repository.getSize() + 1; i++) {
            try {
                productsProxy.add(repository.getId(i));
            } catch (ProductException e) {
                e.printStackTrace();
            }
        }

        ArgsParserImpl ap = new ArgsParserImpl();
        OrderCreatorImpl oc = new OrderCreatorImpl();
        List<String> list = null;

        try {
            list = ap.parsParams(resultCheck);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        Map<String, Integer> map = null;
        try {
            map = oc.makeOrder(list);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        CheckImpl check = new CheckImpl(map);
        StringBuilder sb = check.showCheck(products);
        StringBuilder html = check.htmlCheck(products);
        System.out.println(sb);

        try {
            writer.print(html);
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        super.doPost(req, resp);
    }
}
