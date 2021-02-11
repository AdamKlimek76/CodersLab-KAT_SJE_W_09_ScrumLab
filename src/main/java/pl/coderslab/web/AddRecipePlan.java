package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/recipe/plan/add")
public class AddRecipePlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int planId = Integer.parseInt(request.getParameter("plan"));
            int recipeId = Integer.parseInt(request.getParameter("recipe"));
            int dayNameId = Integer.parseInt(request.getParameter("dayName"));
            String mealName = request.getParameter("mealName");
            int displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
            RecipePlan recipePlan = new RecipePlan(1, recipeId, mealName, displayOrder, dayNameId, planId);
            RecipePlanDao recipePlanDao = new RecipePlanDao();
            recipePlanDao.create(recipePlan);
            response.sendRedirect("/app/recipe/plan/add");
        } catch (Exception e) {
            response.sendRedirect("/app/recipe/plan/add");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        int adminId = admin.getId();

        PlanDao planDao = new PlanDao();
        List<Plan> planList = planDao.readPlansByAdminId(adminId);
        session.setAttribute("planList", planList);

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipeList = recipeDao.readRecipeByAdminId(adminId);
        session.setAttribute("recipeList", recipeList);

        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNameList = dayNameDao.findAll();
        session.setAttribute("dayNameList", dayNameList);


        getServletContext().getRequestDispatcher("/addRecipeToPlan.jsp").forward(request, response);
    }
}
