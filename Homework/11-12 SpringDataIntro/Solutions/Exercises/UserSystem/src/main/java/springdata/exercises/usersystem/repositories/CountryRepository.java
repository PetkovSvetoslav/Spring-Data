package springdata.exercises.usersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.exercises.usersystem.models.location.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
