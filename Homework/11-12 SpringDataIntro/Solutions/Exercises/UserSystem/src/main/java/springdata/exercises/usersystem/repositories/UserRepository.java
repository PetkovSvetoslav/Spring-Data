package springdata.exercises.usersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springdata.exercises.usersystem.models.User;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User AS u WHERE u.email LIKE CONCAT('%@', :emailProvider)")
    List<User> findAllByEmailProvider(@Param("emailProvider") String emailProvider);

    @Modifying
    @Query("UPDATE User AS u SET u.deleted = TRUE WHERE u.lastTimeLoggedIn > :date")
    void setAllInactiveUsersAfterDateAsDeleted(@Param("date") Date date);

    @Modifying
    @Query("DELETE FROM User AS u WHERE u.deleted = TRUE")
    int deleteAllByIsDeletedTrue();
}
