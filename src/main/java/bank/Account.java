package bank;

import bank.exceptions.AmountErrorException;

public class Account {

  private int id;
  private String type;
  private double balance;

  public Account(int id, String type, double balance) {
    this.id = id;
    this.type = type;
    this.balance = balance;
  }


  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(double amount) throws AmountErrorException {
    if (amount < 1) {
      throw new AmountErrorException ("Cannot deposit a negative amount");
    } else {
      double newBalance = balance + amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }
  }

  public void withdraw(double amount) throws AmountErrorException {
    if (amount < 0) {
      throw new AmountErrorException("Cannot withdraw a negative amount");
    } else if (amount > getBalance()){
      throw new AmountErrorException("Insufficient funds");
    }else {
      double newBalance = balance - amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }
  }
}
