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
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            System.out.println(admin);
            int adminId = admin.getId();
            System.out.println(adminId);
            String name = request.getParameter("name");
            System.out.println(name);
            String description = request.getParameter("description");
            System.out.println(description);
            Integer preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
            System.out.println(preparationTime);
            String preparation = request.getParameter("preparation");
            System.out.println(preparation);
            String ingredients = request.getParameter("ingredients");
            System.out.println(ingredients);
            LocalDateTime localDateTime=LocalDateTime.now();

            Timestamp created = Timestamp.valueOf(localDateTime);
            System.out.println(created);
            Timestamp updated = Timestamp.valueOf(localDateTime);
            System.out.println(updated);

            Recipe recipe = new Recipe(1, name, ingredients, description, created, updated, preparationTime, preparation, adminId);
            System.out.println(recipe);
            RecipeDao recipeDao = new RecipeDao();
            recipeDao.create(recipe);
            response.sendRedirect("/app/recipe/list");
        } catch (NullPointerException e) {
            //System.out.println("nie wprowadzono danych");
            response.sendRedirect("/app/recipe/add");
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
