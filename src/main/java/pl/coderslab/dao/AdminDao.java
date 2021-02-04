package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Book;
import pl.coderslab.utils.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String FIND_ADMIN_QUERY = "SELECT * from admins where email = ?;";
    private static final String CREATE_ADMINS_QUERY="INSERT INTO admins(first_name, last_name, email, password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMINS_QUERY = "UPDATE admins SET first_name = ? , last_name = ?," +
            " email = ?, password = ?, superadmin = ?, enable = ? WHERE id = ?;";

    public String hashPassword(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public Admin create(Admin admin) {
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement createdstatement = connection.prepareStatement(CREATE_ADMINS_QUERY,PreparedStatement.RETURN_GENERATED_KEYS)){
            { createdstatement.setString(1,admin.getFirstName());
            createdstatement.setString(2,admin.getLastName());
            createdstatement.setString(3,admin.getEmail());
            createdstatement.setString(4,admin.getPassword());
            createdstatement.setInt(5,admin.getSuperAdmin());
            createdstatement.setInt(6,admin.getEnable());
                int result =createdstatement.executeUpdate();
                if(result!=1){
                    throw  new RuntimeException("Execute update returned"+result);

                }
                try(ResultSet generatedKeys=createdstatement.getGeneratedKeys()){
                    if(generatedKeys.first()){
                        admin.setId(generatedKeys.getInt(1));
                        return admin;
                    } else{
                        throw  new RuntimeException("Generated key was not foung");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;


    }
    public void updateAdmins(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMINS_QUERY)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPassword());
            statement.setInt(5, admin.getSuperAdmin());
            statement.setInt(6, admin.getEnable());
            statement.setInt(7, admin.getId());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Admin read(Integer adminID){
        Admin admin=new Admin();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement readStatement = connection.prepareStatement(READ_ADMIN_QUERY)){
            readStatement.setInt(1,adminID);
            try(ResultSet result=readStatement.executeQuery()){
                while (result.next()){
                    admin.setFirstName(result.getString("first_name"));
                    admin.setLastName(result.getString("lastname"));
                    admin.setEmail(result.getString("email"));
                    admin.setPassword(result.getString("password"));
                    admin.setSuperAdmin(result.getInt("superadmin"));
                    admin.setEnable(result.getInt("enable"));
                }
            } catch (Exception e ){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
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
