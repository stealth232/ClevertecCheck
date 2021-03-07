package ru.clevertec.check.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.parameters.ProductParameters;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin_product")
public class AdminProductServlet extends HttpServlet {
    static Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stockString = req.getParameter("stock");
        String idString = req.getParameter("id");
        Boolean stock = Boolean.parseBoolean(stockString);
        Integer id = Integer.valueOf(idString);
        List<ProductParameters> products;
        String message = null;
        ProductParameters product;
        RequestDispatcher request = req.getRequestDispatcher("/pages/admin/admin_message_product.jsp");
        if (!idString.isEmpty() && !stockString.isEmpty()) {
            if (Repository.updateStockById(id, stock)) {
                message = "Updating stock product id " + id;
            } else {
                message = "No product with id " + id;
            }
        }
        products = Repository.getCurrentProductList();
        req.setAttribute("message", message);
        req.setAttribute("products", products);
        request.forward(req, resp);
    }
}
