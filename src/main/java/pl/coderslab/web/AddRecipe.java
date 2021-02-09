package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/app/recipe/add")
public class AddRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            int adminId = admin.getId();
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int preparationTime = Integer.getInteger(request.getParameter("minutes"));
            String preparation = request.getParameter("preparation");
            String ingedients = request.getParameter("ingedients");

            Timestamp created = Timestamp.valueOf("08.01.2021");
            Timestamp updated = Timestamp.valueOf("08.01.2021");

            Recipe recipe = new Recipe(1, name, ingedients, description, created, updated, preparationTime, preparation, adminId);
            RecipeDao recipeDao = new RecipeDao();
            recipeDao.create(recipe);
        } catch (NullPointerException e) {
            System.out.println("nie wprowadzono danych");
            response.sendRedirect("addrecipe.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else
            getServletContext().getRequestDispatcher("/addrecipe.jsp").forward(request, response);


    }
}
