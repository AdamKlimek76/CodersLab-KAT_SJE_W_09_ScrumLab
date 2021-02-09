package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.LastPlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Admin admin = getAdmin(email);
            if (admin == null) {
                response.sendRedirect("/login");
            } else if (!admin.getPassword().equals(password)) {
                response.sendRedirect("/login");
            } else {
                try {
                    Integer numberOfRecipes = readNumberOfAdminRecipes(admin);
                    Integer numberOfPlans = readNumberOfAdminPlans(admin);
                    List<LastPlan> lastPlanDetails = readLastPlanDetails(admin);


                    String lastPlanName = lastPlanDetails.get(0).getPlanName();

                    Set<String> days = new LinkedHashSet<>();
                    for (int i = 0; i < lastPlanDetails.size(); i++) {
                        days.add(lastPlanDetails.get(i).getDayName());
                    }

                    setSessionAttributes(request, admin, numberOfRecipes, numberOfPlans, lastPlanDetails, lastPlanName, days);
                } catch (Exception e) {
                    Integer numberOfRecipes = 0;
                    Integer numberOfPlans = 0;
                    List<LastPlan> lastPlanDetails = Arrays
                            .asList(new LastPlan(0, "Dodaj swój pierwszy plan", "", "", "", ""));
                    String lastPlanName = "Dodaj swój pierszy plan";
                    Set<String> days = new LinkedHashSet<>();
                    days.add("");
                    setSessionAttributes(request, admin, numberOfRecipes, numberOfPlans, lastPlanDetails, lastPlanName, days);

                    getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
                }
                getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
            }
        } catch (NullPointerException e) {
            System.out.println("null pointer exeption");
            response.sendRedirect("/login");

        }
    }

    private void setSessionAttributes(HttpServletRequest request, Admin admin, Integer numberOfRecipes, Integer numberOfPlans, List<LastPlan> lastPlanDetails, String lastPlanName, Set<String> days) {
        HttpSession session = request.getSession();
        session.setAttribute("admin", admin);
        session.setAttribute("lastPlanDetails", lastPlanDetails);
        session.setAttribute("days", days);
        session.setAttribute("numberOfRecipes", numberOfRecipes);
        session.setAttribute("numberOfPlans", numberOfPlans);
        session.setAttribute("lastPlanName", lastPlanName);
    }

    private List<LastPlan> readLastPlanDetails(Admin admin) {
        PlanDao planDao = new PlanDao();
        return planDao.readLastPlan(admin.getId());
    }

    private Integer readNumberOfAdminRecipes(Admin admin) {
        RecipeDao recipeDao = new RecipeDao();
        return recipeDao.readSize(admin.getId());
    }

    private Integer readNumberOfAdminPlans(Admin admin) {
        PlanDao planDao = new PlanDao();
        return planDao.numAddedPlans(admin.getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private Admin getAdmin(String email) {
        AdminDao adminDao = new AdminDao();
        return adminDao.findAdminByEmail(email);
    }

}
