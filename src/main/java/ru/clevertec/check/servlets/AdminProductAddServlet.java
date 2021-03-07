package ru.clevertec.check.servlets;

import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.parameters.ProductParameters;
import ru.clevertec.check.entities.product.Product;
import ru.clevertec.check.exception.ProductException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin_addproduct")
public class AdminProductAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String stockString = req.getParameter("stock");
        String costString = req.getParameter("cost");
        List<ProductParameters> products;
        String message = null;
        ProductParameters product;
        RequestDispatcher request = req.getRequestDispatcher("/pages/admin/admin_message_product.jsp");
        if (name != null && stockString != null && costString != null) {
            double cost = Double.parseDouble(costString);
            boolean stock = Boolean.parseBoolean(stockString);
            product = new Product(name, cost, stock);
            try {
                if (Repository.insert(product)) {
                    message = "Succesfully added product " + name;
                }
            } catch (ProductException e) {
                message = "Product is exist";
            }
        }
        products = Repository.getCurrentProductList();
        req.setAttribute("message", message);
        req.setAttribute("products", products);
        request.forward(req, resp);
    }
}
