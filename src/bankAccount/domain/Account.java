package bankAccount.domain;

import bankAccount.exception.AccountBlockedException;
import bankAccount.exception.IllegalStatusChangeException;
import bankAccount.exception.IllegalTransferDataException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public abstract class Account implements Transferable {
    protected final String id;
    protected final String ownerName;
    private double balance;
    protected AccountStatus status;
    private List<Transaction> transactions;

    public Account(String id, String ownerName) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }

        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("ownerName cannot be null or blank");
        }

        this.id = id;
        this.ownerName = ownerName;
        this.status = AccountStatus.ACTIVE;
        this.transactions = new ArrayList<>();
    }

    protected void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    protected void increaseBalance(double amount) {
        balance += amount;
    }

    protected void decreaseBalance(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        if (status != AccountStatus.ACTIVE) {
            throw new AccountBlockedException("To make an deposit, your account must be ACTIVE");
        }

        increaseBalance(amount);
        addTransaction(new Transaction(TransactionType.DEPOSIT, amount, LocalDateTime.now(), "Deposit made"));
    }

    public abstract void withdraw(double amount);

    public void validateWithdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        if (status != AccountStatus.ACTIVE) {
            throw new AccountBlockedException("To make an withdraw, your account must be ACTIVE");
        }
    }

    public void block() {
        if (this.status == AccountStatus.CLOSED) {
            throw new IllegalStatusChangeException("Closed account cannot be blocked");
        }

        this.status = AccountStatus.BLOCKED;
    }

    public void close() {
        status = AccountStatus.CLOSED;
    }

    @Override
    public String toString() {
        return "Owner: " + getOwnerName() + " | Balance: " + getBalance() + " | Status: " + getStatus();
    }

    @Override
    public void transfer(Account to, double amount) {
        if (to == null) {
            throw new IllegalTransferDataException("to cannot be null");
        }
        if (this.id.equals(to.id)) {
            throw new IllegalTransferDataException("Cannot transfer to the same account");
        }
        if (to.status != AccountStatus.ACTIVE) {
            throw new IllegalTransferDataException("Target account must be ACTIVE");
        }

        this.withdraw(amount);
        to.deposit(amount);

        addTransaction(new Transaction(TransactionType.TRANSFER, amount, LocalDateTime.now(), "Transfer to account: " + to.getId()));
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public String getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
