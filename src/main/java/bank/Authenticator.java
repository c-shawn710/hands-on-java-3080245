package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {

  public static Customer login(String username, String password) throws LoginException {
    Customer customer = DataSource.getCustomer(username);

    if (username == null) {
      throw new LoginException("Username not found.");
    }

    if (password.equals(customer.getPassword())) {
      customer.setAuthenticated(true);
      return customer;
    } else {
      throw new LoginException("Incorrect password.");
    }
  }

  public static void logout(Customer customer) {
    customer.setAuthenticated(false);
  }
}
