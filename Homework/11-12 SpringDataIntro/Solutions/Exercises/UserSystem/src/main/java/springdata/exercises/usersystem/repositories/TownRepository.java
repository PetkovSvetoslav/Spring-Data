package springdata.exercises.usersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.exercises.usersystem.models.location.Town;

public interface TownRepository extends JpaRepository<Town, Long> {
}
