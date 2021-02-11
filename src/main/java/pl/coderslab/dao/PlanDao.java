package pl.coderslab.dao;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.LastPlan;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

    //ZAPYTANIA SQL
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY =
            "UPDATE	plan SET name = ?, description = ? , created = ?, admin_id = ? WHERE id = ?;";
    private static final String READ_LAST_PLAN_QUERY = "SELECT recipe_plan.id as id, plan.name as name_plan, day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM recipe_plan\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id\n" +
            "JOIN plan on recipe_plan.plan_id = plan.id\n" +
            "WHERE recipe_plan.plan_id = (SELECT MAX(plan.id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    private static final String ALL_ADMIN_PLANS = "SELECT * FROM plan WHERE admin_id = ? ;";
    private static final String READ_PLANS_BY_ADMIN_ID_QUERY = "SELECT * from plan where admin_id = ?;";
    private static final String READ_PLAN_DETAILS_QUERY = "SELECT plan.id as plan_id, recipe.id as recipe_id, plan.name as plan_name,\n" +
            "       plan.description as plan_description, recipe.name as recipe_name,\n" +
            "       recipe.ingredients as recipe_ingredients, recipe.description as recipe_description,\n" +
            "       recipe.preparation_time as preparation_time,\n" +
            "       recipe.preparation as recipe_preparation, day_name.name as day_name, meal_name\n" +
            "FROM recipe_plan\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id\n" +
            "JOIN plan on recipe_plan.plan_id = plan.id\n" +
            "WHERE recipe_plan.plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    /**
     * Get plan by id
     *
     * @param planId
     * @return
     */
    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getTimestamp("created"));
                    plan.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    /**
     * Return all plans
     *
     * @return
     */
    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getTimestamp("created"));
                planToAdd.setAdminId(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }


    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setTimestamp(3, plan.getCreated());
            insertStm.setInt(4, plan.getAdminId());
            int result = insertStm.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            // setPlanIdGeneratedFromDatabase(plan, insertStm);
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    private void setPlanIdGeneratedFromDatabase(Plan plan, PreparedStatement prepStat) throws SQLException {
//        ResultSet databaseId = prepStat.getGeneratedKeys();
//        if (databaseId.next()) {
//            int generatedId = databaseId.getInt(1);
//            int generatedAdminId = databaseId.getInt(5);
//            plan.setId(generatedId);
//            plan.setAdminId(generatedAdminId);
//            System.out.println("Inserted ID: " + generatedId);
//            System.out.println("Inserted adminID: " + generatedAdminId);
//        } else {
//            throw new RuntimeException("Generated key was not found");
//        }
//    }

    /**
     * Remove plan by id
     *
     * @param planId
     */
    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update plan
     *
     * @param plan
     */
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(5, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setTimestamp(3, plan.getCreated());
            statement.setInt(4, plan.getAdminId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<LastPlan> readLastPlan(int adminId) {
        List<LastPlan> planList = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_LAST_PLAN_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LastPlan lastPlan = new LastPlan();
                    lastPlan.setId(resultSet.getInt("id"));
                    lastPlan.setPlanName(resultSet.getString("name_plan"));
                    lastPlan.setDayName(resultSet.getString("day_name"));
                    lastPlan.setMealName(resultSet.getString("meal_name"));
                    lastPlan.setRecipeName(resultSet.getString("recipe_name"));
                    lastPlan.setRecipeDescription(resultSet.getString("recipe_description"));
                    planList.add(lastPlan);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return planList;
    }


    public int numAddedPlans(int adminId) {
        int counter = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(ALL_ADMIN_PLANS)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }

    public List<Plan> readPlansByAdminId(Integer adminId) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLANS_BY_ADMIN_ID_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Plan plan = new Plan();
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getTimestamp("created"));
                    plan.setAdminId(resultSet.getInt("admin_id"));
                    planList.add(plan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planList;

    }

    public List<PlanDetails> readPlanDetails(Integer planId) {
        List<PlanDetails> planDetailsList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_DETAILS_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlanDetails planDetails = new PlanDetails();
                    planDetails.setPlanId(resultSet.getInt("plan_id"));
                    planDetails.setRecipeId(resultSet.getInt("recipe_id"));
                    planDetails.setPlanName(resultSet.getString("plan_name"));
                    planDetails.setPlanDescription(resultSet.getString("plan_description"));
                    planDetails.setRecipeName(resultSet.getString("recipe_name"));
                    planDetails.setRecipeIngredients(resultSet.getString("recipe_ingredients"));
                    planDetails.setRecipeDescription(resultSet.getString("recipe_description"));
                    planDetails.setPreparationTime(resultSet.getInt("preparation_time"));
                    planDetails.setRecipePreparation(resultSet.getString("recipe_preparation"));
                    planDetails.setDayName(resultSet.getString("day_name"));
                    planDetails.setMealName(resultSet.getString("meal_name"));
                    planDetailsList.add(planDetails);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planDetailsList;

    }



}
