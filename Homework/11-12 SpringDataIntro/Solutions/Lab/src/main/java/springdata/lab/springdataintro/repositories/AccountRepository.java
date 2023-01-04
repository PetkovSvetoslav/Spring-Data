package springdata.lab.springdataintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.lab.springdataintro.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
