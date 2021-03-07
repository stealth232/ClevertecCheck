package ru.clevertec.check.servlets;

import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin_user")
public class AdminUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String loginToChange = req.getParameter("logintochange");
        String userType = req.getParameter("role");
        List<User> users;
        String message = null;
        RequestDispatcher request = req.getRequestDispatcher("/pages/admin/admin_message.jsp");
        if (login != null) {
            if (Repository.deleteByLogin(login)) {
                message = "Delete " + login + " was succesfully";
            } else {
                message = "No user  " + login + " in our DB";
            }
        }
        if (loginToChange != null) {
            if (Repository.updateUserRole(loginToChange, userType)) {
                message = "User " + loginToChange + " updated to " + userType.toString();
            } else {
                message = "No user " + loginToChange + " or no role " + userType;
            }
        }
        users = Repository.getCurrentUserList();
        req.setAttribute("message", message);
        req.setAttribute("users", users);
        request.forward(req, resp);

    }
}
