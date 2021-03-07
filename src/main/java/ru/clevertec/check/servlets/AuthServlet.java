package ru.clevertec.check.servlets;

import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.entities.user.UserType;
import ru.clevertec.check.exception.ProductException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = Repository.getUserByLogin(login, password);
            if (user != null && user.getUserType().equals(UserType.USER)) {
                RequestDispatcher requestDispatcherUser = req.getRequestDispatcher("/pages/shop.jsp");
                req.setAttribute("user", user);
                requestDispatcherUser.forward(req, resp);
            } else if (user != null && user.getUserType().equals(UserType.ADMIN)) {
                RequestDispatcher requestDispatcherAdmin = req.getRequestDispatcher("/pages/admin/controller.jsp");
                req.setAttribute("user", user);
                requestDispatcherAdmin.forward(req, resp);
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/errors/authorization_error.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (ProductException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/errors/login_error.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
