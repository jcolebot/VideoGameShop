package videogameshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserOptions {
    Scanner scan = new Scanner(System.in);
    String query;
    List<ViewGames> ownedList = new ArrayList<ViewGames>();

    public void makeOffer() {
        System.out.print("Enter Game ID you wish to buy: ");
        int inputGame = scan.nextInt();
        System.out.print("What would you like to call this game?: ");
        String gameName = scan.nextLine();
        System.out.print("How much would you like to bid?: ");
        double bidAmount = scan.nextDouble();

        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            query = "INSERT INTO bids (gameid, owned, ownedprice) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, inputGame);
            statement.setString(2, gameName);
            statement.setDouble(2, bidAmount);

            statement.execute();
            System.out.println("New game added to inventory!");
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

    public void viewOwnedGames() {
            Connection connection = null;
            try {
                connection = DbConnection.getConnection();
                query = "SELECT * FROM users WHERE NAME = owned";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameid");
                    String ownedTitle = resultSet.getString("owned");
                    double ownedPrice = resultSet.getDouble("ownedprice");

                    ownedList.add(new ViewGames(gameID, ownedTitle, ownedPrice));
                }
                for (int i = 0; i < ownedList.size(); i++) {
                    System.out.println("Game \n" +
                            "Title " + ownedList.get(i).getTitle() + "\n");
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
    }
