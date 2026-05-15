package bankAccount.domain;

public interface Transferable {
    void transfer(Account to, double amount);
}
