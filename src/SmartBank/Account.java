package SmartBank;

public class Account {
    private int accountNumber;
    private double balance;
    private Customer customer;

    public Account(int accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = 0;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void deposit(double amount) {
        if(amount <= 0) throw new IllegalArgumentException("Deposit must be positive.");
        balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        if(amount <= 0) throw new IllegalArgumentException("Withdraw must be positive.");
        if(amount > balance) throw new Exception("Insufficient balance.");
        balance -= amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "AccountNumber=" + accountNumber +
                ", Balance=" + balance +
                ", Customer=" + customer +
                '}';
    }
}
