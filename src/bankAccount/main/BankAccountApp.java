package bankAccount.main;

import bankAccount.domain.Account;
import bankAccount.domain.CheckingAccount;
import bankAccount.domain.SavingsAccount;
import bankAccount.domain.Transaction;
import bankAccount.service.AccountService;

import java.util.Scanner;

public class BankAccountApp {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountService accountService = new AccountService();

        boolean running = true;


        while (running) {
            try {
                System.out.println("\n=== BANK SYSTEM ===");
                System.out.println("1 - Create Checking Account");
                System.out.println("2 - Create Savings Account");
                System.out.println("3 - Deposit");
                System.out.println("4 - Withdraw");
                System.out.println("5 - Transfer");
                System.out.println("6 - Block Account");
                System.out.println("7 - Close Account");
                System.out.println("8 - List Accounts");
                System.out.println("9 - Show Transactions");
                System.out.println("0 - Exit");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        System.out.println("Owner Name:");
                        String ownerName = scanner.nextLine();

                        System.out.println("Overdraft Limit:");
                        double overdraft = scanner.nextDouble();
                        scanner.nextLine();

                        CheckingAccount checkingAccount = new CheckingAccount(id, ownerName, overdraft);
                        accountService.register(checkingAccount);
                        System.out.println("Checking Account Created");

                        break;
                    }
                    case 2: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        System.out.println("Owner Name:");
                        String ownerName = scanner.nextLine();

                        System.out.println("Interest Rate:");
                        double interestRate = scanner.nextDouble();
                        scanner.nextLine();

                        SavingsAccount savingsAccount = new SavingsAccount(id, ownerName, interestRate);
                        accountService.register(savingsAccount);
                        System.out.println("Savings Account Created");

                        break;
                    }
                    case 3: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        System.out.println("Amount:");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        accountService.deposit(id, amount);
                        System.out.println("Deposit successful");
                        break;
                    }
                    case 4: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        System.out.println("Amount:");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        accountService.withdraw(id, amount);
                        System.out.println("Withdraw successful");

                        break;
                    }
                    case 5: {
                        System.out.println("From account:");
                        String from = scanner.nextLine();

                        System.out.println("To account:");
                        String to = scanner.nextLine();

                        System.out.println("Amount:");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        accountService.transfer(from, to, amount);
                        System.out.println("Transfer successful"
                                + " from " + from + " to " + to);

                        break;
                    }

                    case 6: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        System.out.println("Are you sure you want to block your account? Y/N:");
                        char confirm = Character.toLowerCase(scanner.nextLine().charAt(0));

                        while (confirm != 'y' && confirm != 'n') {
                            System.out.println("Invalid option, reply with Y/N:");
                            confirm = Character.toLowerCase(scanner.nextLine().charAt(0));
                        }

                        if (confirm == 'y') {
                            accountService.blockAccount(id);
                            System.out.println("Account blocked!");
                        } else {
                            System.out.println("Back to main menu");
                        }

                        break;
                    }
                    case 7: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        System.out.println("Are you sure you want to close your account? Y/N:");
                        char confirm = Character.toLowerCase(scanner.nextLine().charAt(0));
                        while (confirm != 'y' && confirm != 'n') {
                            System.out.println("Invalid option, reply with Y/N:");
                            confirm = Character.toLowerCase(scanner.nextLine().charAt(0));
                        }
                        if (confirm == 'y') {
                            accountService.closeAccount(id);
                            System.out.println("Account closed");

                        } else {
                            System.out.println("Back to main menu");
                        }

                        break;
                    }
                    case 8: {
                        for (Account account : accountService.listAccounts()) {
                            System.out.println(account);
                        }
                        break;
                    }
                    case 9: {
                        System.out.println("Account ID:");
                        String id = scanner.nextLine();

                        Account account = accountService.findById(id);
                        if (account.getTransactions().isEmpty()) {
                            System.out.println("Your account has no transactions!");
                            break;
                        }
                        for (Transaction transaction : account.getTransactions()) {
                            System.out.println(transaction);
                        }
                        break;
                    }
                    case 0: {
                        System.out.println("System closed");
                        running = false;
                        break;
                    }
                    default: {
                        System.out.println("Invalid option");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
