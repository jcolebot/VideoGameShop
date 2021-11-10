package videogameshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EmployeeDbLogic {
    Scanner scan = new Scanner(System.in);
    String query;
    List<ViewGames> list = new ArrayList<ViewGames>();

    public void viewInventory() {
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            query = "SELECT * FROM gameinventory";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int gameID = resultSet.getInt("gameid");
                String gameTitle = resultSet.getString("title");
                double gamePrice = resultSet.getDouble("price");

                list.add(new ViewGames(gameID, gameTitle, gamePrice));
            }
            for (int i = 0; i < list.size(); i++) {
                System.out.println("Game \n" +
                        "ID: " + list.get(i).getGameId() + "\n" +
                        "Title " + list.get(i).getTitle() + "\n" +
                        "Price: " + list.get(i).getPrice() + "\n");
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

    public void addGame() {

        System.out.print("Enter game title: ");
        String gameTitle = scan.next();

        System.out.print("Enter the price: ");
        double gamePrice = scan.nextDouble();

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            query = "INSERT INTO gameinventory (title, price) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameTitle);
            statement.setDouble(2, gamePrice);

            statement.execute();
            System.out.println("New game added to inventory.");
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
    }

    public void removeGame() {
        Connection connection = null;

        System.out.print("Enter game ID to be removed: ");
        double deleteId = scan.nextDouble();

        try {
            connection = DbConnection.getConnection();
            query = "DELETE FROM inventory WHERE gameid = " + deleteId;
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
}
