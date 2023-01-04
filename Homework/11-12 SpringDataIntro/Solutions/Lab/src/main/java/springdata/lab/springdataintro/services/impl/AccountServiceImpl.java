package springdata.lab.springdataintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.lab.springdataintro.exeptions.InvalidAccountOperationException;
import springdata.lab.springdataintro.exeptions.NonExistingEntityException;
import springdata.lab.springdataintro.models.Account;
import springdata.lab.springdataintro.models.User;
import springdata.lab.springdataintro.repositories.AccountRepository;
import springdata.lab.springdataintro.services.AccountService;

import java.math.BigDecimal;

@Transactional()
@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createUserAccount(User user, Account account) {
        account.setUser(user);
        user.getAccounts().add(account);
        this.accountRepository.save(account);
    } // commit transaction - @Transactional

    @Override
    public void withdrawMoney(BigDecimal amount, Long accountId) {
        Account account = this.accountRepository.findById(accountId).orElseThrow(() ->
                new NonExistingEntityException(
                        String.format("Entity with Id: %d does not exist.", accountId)
                ));

        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new InvalidAccountOperationException(
                    String.format("The desired amount (%s) is greater than the current account balance (%s)",
                            balance, amount)
            );
        }

        account.setBalance(balance.subtract(amount));
    } // commit transaction - @Transactional

    @Override
    public void depositMoney(BigDecimal amount, Long accountId) {
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new IllegalArgumentException("Insufficient amount.");
        }

        Account account = this.accountRepository.findById(accountId).orElseThrow(() ->
                new InvalidAccountOperationException(
                        String.format("Entity with Id: %d does not exist.", accountId)
                ));

        BigDecimal balance = account.getBalance();

        account.setBalance(balance.add(amount));
    } // commit transaction - @Transactional

    @Override
    public void transferMoney(BigDecimal amount, Long fromAccountId, Long toAccountId) {
        this.depositMoney(amount, toAccountId);
        this.withdrawMoney(amount, fromAccountId);
    } // commit transaction - @Transactional
}

