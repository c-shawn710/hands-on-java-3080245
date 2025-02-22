package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountErrorException;

public class Menu {
  Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to your bank!");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();
    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username: ");
    String username = scanner.nextLine();

    System.out.println("Please enter your password: ");
    String password = scanner.nextLine();

    Customer customer = null;
    try{
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }
    return customer;
  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("""
          ====================================
          Please select one of the options:
          1 - Deposit
          2 - Withdraw
          3 - View balance
          4 - Exit app
          ====================================
          """);

        selection = scanner.nextInt();
        double amount = 0;

        switch(selection) {
          case 1:
          System.out.println("How much would you like to deposit?: ");
          amount = scanner.nextDouble();

          try {
            account.deposit(amount);
          } catch (AmountErrorException e){
            System.out.println(e.getMessage() + "\nPlease try again.");
          }
          break;

          case 2:
          System.out.println("How much would you like to withdraw?: ");
          amount = scanner.nextDouble();
          try{
          account.withdraw(amount);
          } catch (AmountErrorException e) {
            System.out.println(e.getMessage() + "\nPlease try again.");
          }
          break;

          case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;

          case 4:
          Authenticator.logout(customer);
          System.out.println("Exiting app...");
          break;

          default:
          System.out.println("Invalid option");
          break;
        }
    }
  }
}
