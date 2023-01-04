package springdata.lab.springdataintro.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.lab.springdataintro.models.Account;
import springdata.lab.springdataintro.models.User;
import springdata.lab.springdataintro.services.AccountService;
import springdata.lab.springdataintro.services.UserService;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) {
        User user = new User("Martin", 24);

        Account account = new Account(new BigDecimal("25000"), user);

        this.userService.register(user);
        this.accountService.createUserAccount(user, account);

        this.accountService.withdrawMoney(new BigDecimal("15000"), user.getId());
        this.accountService.depositMoney(new BigDecimal("5000"), user.getId());
    }
}
