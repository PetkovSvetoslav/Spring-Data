package springdata.lab.springdataintro.services;

import springdata.lab.springdataintro.models.Account;
import springdata.lab.springdataintro.models.User;

import java.math.BigDecimal;

public interface AccountService {
    void createUserAccount(User user, Account account);

    void withdrawMoney(BigDecimal amount, Long accountId);

    void depositMoney(BigDecimal amount, Long accountId);

    void transferMoney(BigDecimal amount, Long fromAccountId, Long toAccountId);
}
