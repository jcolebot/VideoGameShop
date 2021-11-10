package videogameshop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import videogameshop.DbConnection;
import videogameshop.User;

public class JDBC_UserRepo implements UserRepo {
    @Override
    public void save(User user) {
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();

            String sql = "INSERT INTO users(name,email,password) values(?,?,?)";
            PreparedStatement prepStat = connection.prepareStatement(sql);
            prepStat.setString(1, user.getName());
            prepStat.setString(2, user.getEmail());
            prepStat.setString(3, user.getPassword());

            int rowCount = prepStat.executeUpdate();
            if(rowCount == 1) {
                System.out.println("New user successfully added!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace(); //show exception details
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email=?";
            PreparedStatement prepStat = connection.prepareStatement(sql);
            prepStat.setString(1, email);

            ResultSet resultSet = prepStat.executeQuery();

            while(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

}
