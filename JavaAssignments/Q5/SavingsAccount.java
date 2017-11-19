public class SavingsAccount {
  private int balance;
  public SavingsAccount() {
    balance = 0;
  }

  public SavingsAccount(int initialBalance) {
    balance = initialBalance;
  }

  public void greet() {
    System.out.println("Hello there");
  }

  public int showBalance() {
    return balance;
  }

  public void deposit(int howMuch) {
    if (howMuch >= 0){
      balance = balance + howMuch;
      System.out.println("new balance is " + balance);
    }
    else {System.out.println("Can’t withdraw a negative amount");}
  }

  public void withdraw(int howMuch) {
    if (howMuch >= 0){
      balance = balance - howMuch;
      System.out.println("new balance is " + balance);
    }
    else {System.out.println("Can’t withdraw a negative amount");}

  }
}
