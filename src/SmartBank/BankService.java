package SmartBank;

import java.util.HashMap;
import java.util.Map;

public class BankService {
    private Map<Integer, Account> accounts = new HashMap<>();
    private int accountCounter = 1001;

    public Account createAccount(String name, String contact) {
        Customer customer = new Customer(name, contact);
        Account account = new Account(accountCounter++, customer);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public void deposit(int accountNumber, double amount) throws InvalidAccountException {
        Account account = accounts.get(accountNumber);
        if(account == null) throw new InvalidAccountException("Account not found.");
        account.deposit(amount);
    }

    public void withdraw(int accountNumber, double amount) throws InvalidAccountException, InsufficientFundsException {
        Account account = accounts.get(accountNumber);
        if(account == null) throw new InvalidAccountException("Account not found.");
        if(amount > account.getBalance()) throw new InsufficientFundsException("Insufficient balance.");
        try {
            account.withdraw(amount);
        } catch (Exception e) {
            throw new InsufficientFundsException(e.getMessage());
        }
    }

    public void transfer(int fromAccount, int toAccount, double amount) throws InvalidAccountException, InsufficientFundsException {
        Account sender = accounts.get(fromAccount);
        Account receiver = accounts.get(toAccount);
        if(sender == null || receiver == null) throw new InvalidAccountException("Invalid account number.");
        if(amount > sender.getBalance()) throw new InsufficientFundsException("Insufficient balance.");
        try {
            sender.withdraw(amount);
            receiver.deposit(amount);
        } catch (Exception e) {
            throw new InsufficientFundsException(e.getMessage());
        }
    }

    public Account getAccount(int accountNumber) throws InvalidAccountException {
        Account account = accounts.get(accountNumber);
        if(account == null) throw new InvalidAccountException("Account not found.");
        return account;
    }

    public Map<Integer, Account> getAllAccounts() {
        return accounts;
    }
}
