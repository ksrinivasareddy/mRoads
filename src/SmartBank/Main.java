package SmartBank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankService();
        int choice = 0;

        do {
            System.out.println("\n--- Smart Bank Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Account");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter contact: ");
                        String contact = sc.nextLine();
                        Account newAccount = bankService.createAccount(name, contact);
                        System.out.println("Account created: " + newAccount);
                        break;
                    case 2:
                        System.out.print("Enter account number: ");
                        int depAcc = sc.nextInt();
                        System.out.print("Enter deposit amount: ");
                        double depAmt = sc.nextDouble();
                        bankService.deposit(depAcc, depAmt);
                        System.out.println("Deposit successful.");
                        break;
                    case 3:
                        System.out.print("Enter account number: ");
                        int witAcc = sc.nextInt();
                        System.out.print("Enter withdrawal amount: ");
                        double witAmt = sc.nextDouble();
                        bankService.withdraw(witAcc, witAmt);
                        System.out.println("Withdrawal successful.");
                        break;
                    case 4:
                        System.out.print("Enter sender account number: ");
                        int fromAcc = sc.nextInt();
                        System.out.print("Enter receiver account number: ");
                        int toAcc = sc.nextInt();
                        System.out.print("Enter transfer amount: ");
                        double transAmt = sc.nextDouble();
                        bankService.transfer(fromAcc, toAcc, transAmt);
                        System.out.println("Transfer successful.");
                        break;
                    case 5:
                        System.out.print("Enter account number: ");
                        int viewAcc = sc.nextInt();
                        Account account = bankService.getAccount(viewAcc);
                        System.out.println(account);
                        break;
                    case 6:
                        System.out.println("Exiting... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InvalidAccountException | InsufficientFundsException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 6);

        sc.close();
    }
}
