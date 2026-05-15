package bankAccount.service;

import bankAccount.domain.Account;

import bankAccount.exception.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private List<Account> accounts = new ArrayList<>();

    public void register(Account account) {
        for (Account acc : accounts) {
            if (acc.getId().equals(account.getId())) {
                throw new IllegalArgumentException("account id already exists");
            }
        }

        accounts.add(account);
    }

    public Account findById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        throw new AccountNotFoundException("No account found with id: " + id);
    }

    public void deposit(String id, double amount) {
        findById(id).deposit(amount);
    };

    public void withdraw(String id, double amount) {
        findById(id).withdraw(amount);
    };

    public void transfer(String fromId, String toId, double amount) {
        Account fromAccount = findById(fromId);
        Account toAccount = findById(toId);

        fromAccount.transfer(toAccount, amount);
    };

    public List<Account> listAccounts() {
        return new ArrayList<>(accounts);
    }

    public void blockAccount(String id) {
        findById(id).block();
    }

    public void closeAccount(String id) {
        findById(id).close();
    }
}
