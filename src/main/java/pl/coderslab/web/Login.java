package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            AdminDao adminDao = new AdminDao();
            Admin admin = adminDao.findAdminByEmail(email);
            if (admin == null) {
                //System.out.println("null");
                response.sendRedirect("/login");
            } else if (!admin.getPassword().equals(password)) {
                //System.out.println("hasło niezgodne");
                response.sendRedirect("/login");
            } else {
                //System.out.println("ok");
                RecipeDao recipeDao = new RecipeDao();
                Integer numberOfRecipes=recipeDao.readSize(admin.getId());
                PlanDao planDao = new PlanDao();
                List<LastPlan> lastPlanDetails = planDao.readLastPlan(admin.getId());
                String lastPlanName=lastPlanDetails.get(0).getPlanName();
                Set<String> days = new LinkedHashSet<>();
                for (int i = 0; i < lastPlanDetails.size(); i++) {
                    days.add(lastPlanDetails.get(i).getDayName());
                }
                /*
                for (String day : days) {
                    System.out.println(day);
                }
                */
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                session.setAttribute("lastPlanDetails", lastPlanDetails);
                session.setAttribute("days", days);
                session.setAttribute("numberOfRecipes",  numberOfRecipes);
                session.setAttribute("lastPlanName", lastPlanName);
                getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
            }
        } catch (NullPointerException e) {
            //System.out.println("nie wprowadzono hasło bądz loginu");
            response.sendRedirect("/login");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
