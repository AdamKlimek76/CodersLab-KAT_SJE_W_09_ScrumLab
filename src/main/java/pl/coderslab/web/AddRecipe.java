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
import java.time.LocalDateTime;

@WebServlet("/app/recipe/add")
public class AddRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        try {
            int adminId = admin.getId();
            String recipeName = request.getParameter("recipeName");
            if (recipeName == null) {
                throw new NullPointerException();
            }
            int preparationTime = 0;
            String description = request.getParameter("description");
            try {
                 preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
            } catch (NumberFormatException e) {
                 preparationTime = 0;
            }

            String preparation = request.getParameter("preparation");
            String ingredients = request.getParameter("ingredients");

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp created = Timestamp.valueOf(localDateTime);

            Recipe recipe = new Recipe(1, recipeName, ingredients, description, created, created, preparationTime, preparation, adminId);
            RecipeDao recipeDao = new RecipeDao();
            recipeDao.create(recipe);
            response.sendRedirect("/app/recipe/list");
        } catch (NullPointerException e) {
            System.out.println("nie wprowadzono danych");
            System.out.println(admin);
            response.sendRedirect("/app/recipe/add");
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/addrecipe.jsp").forward(request, response);
        }
    }
}
