package bankAccount.domain;

import bankAccount.exception.InsufficientFundsException;
import java.time.LocalDateTime;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String id, String ownerName, double interestRate) {
        super(id, ownerName);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = getBalance() * (interestRate / 100);
        increaseBalance(interest);

        addTransaction(new Transaction(TransactionType.INTEREST, interest, LocalDateTime.now(), "Interest applied"));
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override

    public void withdraw(double amount) {
        validateWithdraw(amount);

        if (amount > getBalance()) {
            throw new InsufficientFundsException("Insufficient Funds");
        }

        decreaseBalance(amount);

        addTransaction(new Transaction(TransactionType.WITHDRAW, amount, LocalDateTime.now(), "Withdraw from savings account"));
    }

    @Override
    public String toString() {
        return super.toString() + " | interestRate: " + interestRate;
    }
}
