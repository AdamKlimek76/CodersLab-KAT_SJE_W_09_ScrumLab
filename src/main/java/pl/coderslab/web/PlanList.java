package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/plan/list")
public class PlanList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            Admin admin=(Admin)session.getAttribute("admin");
            PlanDao planDao=new PlanDao();
            List<Plan>plans=planDao.readPlansByAdminId(admin.getId());
            session.setAttribute("plans", plans);
            getServletContext().getRequestDispatcher("/appPlans.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
