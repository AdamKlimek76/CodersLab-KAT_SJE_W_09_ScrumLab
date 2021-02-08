package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(name = "addPlan", value = "/app/plan/add")
public class addPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/addPlan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planName = request.getParameter("planName");
        String textPlan = request.getParameter("textPlan");

        PlanDao planDao = new PlanDao();

        HttpSession session = request.getSession();
        int adminId = (int) session.getAttribute("admin");
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dateTime);

        Plan plan = new Plan(1, planName, textPlan, timestamp, adminId);
        planDao.create(plan);

        getServletContext().getRequestDispatcher("/app/plan/list").forward(request, response);
    }
}
