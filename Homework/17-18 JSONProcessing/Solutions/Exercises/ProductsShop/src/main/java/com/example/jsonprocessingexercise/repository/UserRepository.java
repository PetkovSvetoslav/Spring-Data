package com.example.jsonprocessingexercise.repository;

import com.example.jsonprocessingexercise.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User AS u " +
            "WHERE (" +
            "   SELECT COUNT(p) FROM Product AS p " +
            "   WHERE p.seller = u " +
            "       AND p.buyer IS NOT NULL " +
            ") > 0 " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllWithMoreThanOneSoldProductOrderedByLastNameThenFirstName();

    @Query("SELECT u FROM User AS u " +
            "JOIN Product AS p " +
            "ON u.id = p.seller.id " +
            "WHERE p.buyer IS NOT NULL " +
            "GROUP BY u.id")
    List<User> findAllWithMoreThanOneSoldProduct();
}
