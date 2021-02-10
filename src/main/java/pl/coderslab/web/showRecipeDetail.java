package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "showRecipeDetail", value = "/app/recipe/details")
public class showRecipeDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RecipeDao recipeDao = new RecipeDao();
        int recipeId = parseToInt(request);
        if (recipeId != 0) {
            Recipe recipe = recipeDao.read(recipeId);
            String ingredients =  recipe.getIngredients();
            List<String> ingredientsList = Arrays.asList(ingredients.split("\n"));
            request.setAttribute("recipe", recipe);
            request.setAttribute("ingredients", ingredientsList);
            getServletContext().getRequestDispatcher("/recipeDetails.jsp").forward(request, response);
        } else {
            response.sendRedirect("/app/recipe/list");
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static int parseToInt(HttpServletRequest request) {
        int recipeId = 0;

        try {
             recipeId = Integer.parseInt(request.getParameter("recipeId"));
        } catch (NumberFormatException e) {
             recipeId = 0;
        }
        return recipeId;
    }
}
