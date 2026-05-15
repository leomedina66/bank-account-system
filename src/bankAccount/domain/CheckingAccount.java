package bankAccount.domain;

import bankAccount.exception.InsufficientFundsException;

import java.time.LocalDateTime;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String id, String ownerName, double overdraftLimit) {
        super(id, ownerName);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        validateWithdraw(amount);
        if (amount > getBalance() + overdraftLimit) {
            throw new InsufficientFundsException("Insufficient Funds");
        }

        decreaseBalance(amount);

        addTransaction(new Transaction(TransactionType.WITHDRAW, amount, LocalDateTime.now(), "Withdraw from checking account"));
    }

    @Override
    public String toString() {
        return super.toString() + " | overdraftLimit: " + overdraftLimit;
    }
}
