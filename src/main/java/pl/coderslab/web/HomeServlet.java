package pl.coderslab.web;

import pl.coderslab.dao.BookDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Book;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Do not change servlet address !!!
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //BookDao bookDao = new BookDao();
       // List<Book> books = bookDao.findAll();
       // System.out.println(books);

     /*   RecipeDao recipeDao = new RecipeDao();
        List<Recipe>recipeList=recipeDao.findAll();
        for (int i = 0; i < recipeList.size(); i++) {
            System.out.println(recipeList.get(i).getName());
        }*/

     ///////////////////////////////////
        /*PlanDao planDao=new PlanDao();

        Plan plan = new Plan(1, "Dieta Klimka", "Opis diety Klimka", Timestamp.valueOf("2020-01-01 21:00:00"), 1);
        planDao.create(plan);


        List<Plan>planList=planDao.findAll();
        for (int i = 0; i <planList.size() ; i++) {
            response.getWriter().println(planList.get(i).getId());
        }*/


        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
    }


}
