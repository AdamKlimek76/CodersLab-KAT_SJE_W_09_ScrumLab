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
import java.sql.Timestamp;
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
        Admin admin = (Admin) session.getAttribute("admin");
        int adminId = admin.getId();

        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dateTime);

        Plan plan = new Plan(1, planName, textPlan, timestamp, adminId);
        planDao.create(plan);

        response.sendRedirect("/app/plan/list");
    }
}
