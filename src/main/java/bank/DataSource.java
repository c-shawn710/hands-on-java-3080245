package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  public static Connection connection() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected!");
    } catch(SQLException e) {
    e.printStackTrace();
    
  }
  return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "SELECT * FROM customers WHERE username = ?";
    Customer customer = null;

    try(Connection connection = connection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

          statement.setString(1, username);

          try(ResultSet resultSet = statement.executeQuery()){
            customer = new Customer(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getString("username"),
              resultSet.getString("password"),
              resultSet.getInt("account_Id"));
          }

    }catch(SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId) {
    String sql = "SELECT * FROM Accounts WHERE ID = ?";
    Account account = null;

    try(Connection connection = connection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

          statement.setInt(1, accountId);

          try(ResultSet resultSet = statement.executeQuery()) {
            account = new Account(
              resultSet.getInt("ID"),
              resultSet.getString("TYPE"),
              resultSet.getDouble("BALANCE"));
          }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void updateAccountBalance(int accountId, double balance) {
    
    String query = "UPDATE ACCOUNTS SET BALANCE = ? WHERE ID = ?";

    try {    
      Connection connection = connection();
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setInt(1, accountId);
      statement.setDouble(2, balance);
      statement.executeUpdate();

    } catch (SQLException e){
      e.printStackTrace();
    }
  }
}
