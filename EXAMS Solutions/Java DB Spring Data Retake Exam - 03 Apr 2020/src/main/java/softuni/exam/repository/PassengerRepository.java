package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Passenger;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    boolean existsByEmail(String email);

    Passenger getPassengerByEmail(String email);

    @Query("SELECT DISTINCT p from Passenger p JOIN FETCH p.tickets t ORDER BY size(t) DESC , p.email")
    List<Passenger> getAllPassengersOrderByTicketsCountDescThenByEmail();
}
