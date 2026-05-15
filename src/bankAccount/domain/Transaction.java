package bankAccount.domain;

import java.time.LocalDateTime;

public class Transaction {
    private TransactionType type;
    private double amount;
    private LocalDateTime timeStamp;
    private String description;

    public Transaction(TransactionType type, double amount, LocalDateTime timeStamp, String description) {
        this.type = type;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.description = description;
    }

    @Override
    public String toString() {
        return "[" + type + "] "
                + amount
                + " | "
                + timeStamp
                + " | "
                + description;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getDescription() {
        return description;
    }
}
