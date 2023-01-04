package springdata.lab.springdataintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.lab.springdataintro.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
