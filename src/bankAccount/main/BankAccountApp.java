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

                showMenu();
                int option = readOption(scanner);

                switch (option) {

                    case 1: {

                        createCheckingAccount(scanner, accountService);

                        break;
                    }
                    case 2: {

                        createSavingsAccount(scanner, accountService);

                        break;
                    }
                    case 3: {
                        deposit(scanner, accountService);

                        break;
                    }
                    case 4: {
                        withdraw(scanner, accountService);

                        break;
                    }
                    case 5: {
                        transfer(scanner, accountService);

                        break;
                    }

                    case 6: {
                        blockAccount(scanner, accountService);

                        break;
                    }
                    case 7: {
                        closeAccount(scanner, accountService);

                        break;
                    }
                    case 8: {
                        listAccounts(scanner, accountService);

                        break;
                    }
                    case 9: {
                        showTransactions(scanner, accountService);

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

    private static void showMenu() {
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
        System.out.println("0 - Exit:");

    }

    private static int readOption(Scanner scanner) {
        int option = scanner.nextInt();
        scanner.nextLine();

        while (option > 9 || option < 0) {
            System.out.println("Invalid option, type a number between 0 and 9:");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        return option;
    }

    private static void createCheckingAccount(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Create Checking Account ===");
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

        pause(scanner);
    }

    private static void createSavingsAccount(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Create Savings Account ===");
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

        pause(scanner);

    }

    private static void deposit(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Deposit ===");
        System.out.println("Account ID:");
        String id = scanner.nextLine();

        System.out.println("Amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        accountService.deposit(id, amount);
        System.out.println("Deposit successful");
        pause(scanner);
    }

    private static void withdraw(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Withdraw ===");
        System.out.println("Account ID:");
        String id = scanner.nextLine();

        System.out.println("Amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        accountService.withdraw(id, amount);
        System.out.println("Withdraw successful");

        pause(scanner);

    }

    private static void transfer(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Transfer ===");
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

        pause(scanner);

    }

    private static void blockAccount(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Block Account ===");
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

        pause(scanner);

    }

    private static void closeAccount(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Close Account ===");
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

        pause(scanner);

    }

    private static void listAccounts(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== List Accounts ===");
        for (Account account : accountService.listAccounts()) {
            System.out.println(account);
        }
        pause(scanner);

    }

    private static void showTransactions(Scanner scanner, AccountService accountService) {
        System.out.println("\n=== Show Transactions ===");
        System.out.println("Account ID:");
        String id = scanner.nextLine();

        Account account = accountService.findById(id);
        if (account.getTransactions().isEmpty()) {
            System.out.println("Your account has no transactions!");
            pause(scanner);
            return;
        }
        for (Transaction transaction : account.getTransactions()) {
            System.out.println(transaction);
        }

        pause(scanner);

    }

    private static void pause(Scanner scanner) {
        System.out.println("Press ENTER to continue:");
        scanner.nextLine();
        System.out.println("\n\n\n\n\n");
    }

}
