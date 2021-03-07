package ru.clevertec.check.servlets;

import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.exception.ProductException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.check.service.CheckConstants.TEXT_HTML;

@WebServlet(urlPatterns = "/create")
public class CreateUserServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Repository.getBaseUserList();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(TEXT_HTML);
        try {
            String firstName = req.getParameter("firstName");
            String secondName = req.getParameter("secondName");
            int age = Integer.valueOf(req.getParameter("age"));
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            if (Repository.isLoginExist(login)) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/errors/login_error.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                Map<String, String> credentials = new HashMap<>();
                credentials.put(login, password);
                User user = new User(firstName, secondName, age, credentials);
                Repository.insert(user);
                req.setAttribute("user", user);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/authorization.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (ProductException e) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/errors/login_error.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
