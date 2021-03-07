package ru.clevertec.check.servlets;

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

@WebServlet(urlPatterns = "/admin_delproduct")
public class AdminProductDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        Integer id = Integer.valueOf(idString);
        List<ProductParameters> products;
        String message = null;
        ProductParameters product;
        RequestDispatcher request = req.getRequestDispatcher("/pages/admin/admin_message_product.jsp");
        if (!idString.isEmpty() ) {
            if (Repository.deleteById(id)) {
                message = "Succesfully deleted product with id " + id;
            } else {
                message = "No product with id " + id + " in DB";
            }
        }
        products = Repository.getCurrentProductList();
        req.setAttribute("message", message);
        req.setAttribute("products", products);
        request.forward(req, resp);
    }
}
