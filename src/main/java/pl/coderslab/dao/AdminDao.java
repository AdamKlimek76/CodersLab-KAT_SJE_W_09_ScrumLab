package pl.coderslab.dao;

import pl.coderslab.model.Admin;
import pl.coderslab.model.Book;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String FIND_ADMIN_QUERY = "SELECT * from admins where email = ?;";


    public Admin create(Admin admin) {
        Admin admin1 = admin;
        return admin1;
    }


    public Admin findAdminByEmail(String email) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ADMIN_QUERY)
        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("first_name"));
                    admin.setLastName(resultSet.getString("last_name"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setSuperAdmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getInt("enable"));
                }
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return admin;

    }
}
