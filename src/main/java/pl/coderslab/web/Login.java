package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            AdminDao adminDao = new AdminDao();
            Admin admin = adminDao.findAdminByEmail(email);
            if (admin == null) {
                System.out.println("null");
                response.sendRedirect("/login");
            } else if (!admin.getPassword().equals(password)) {
                System.out.println("hasło niezgodne");
                response.sendRedirect("/login");
            } else {
                System.out.println("ok");
                HttpSession session=request.getSession();
                session.setAttribute("admin", admin);
                getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
            }
        } catch (NullPointerException e) {
            System.out.println("nie wprowadzono hasło bądz loginu");
            response.sendRedirect("/login");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
