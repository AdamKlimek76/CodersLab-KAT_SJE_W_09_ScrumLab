package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;

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

@WebServlet("/app/plan/details")
public class PlanDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer planId=Integer.parseInt(request.getParameter("planId"));
        PlanDao planDao = new PlanDao();
        List<PlanDetails>planDetailsList=planDao.readPlanDetails(planId);
        Plan plan = planDao.read(planId);
        HttpSession session=request.getSession();
        session.setAttribute("plan", plan);
        session.setAttribute("planDetailsList", planDetailsList);

        Set<String> days = new LinkedHashSet<>();
        for (int i = 0; i < planDetailsList.size(); i++) {
            days.add(planDetailsList.get(i).getDayName());
        }
        getServletContext().getRequestDispatcher("/planDetails.jsp").forward(request, response);
    }
}
