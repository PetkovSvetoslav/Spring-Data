package com.example.xmlcardealer.repository;

import com.example.xmlcardealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer AS c " +
            "ORDER BY c.birthDate, c.isYoungDriver DESC")
    List<Customer> findAllOrderByBirthDateAscThenByNotYoungDriver();

    @Query("SELECT cus.name, COUNT(c), SUM(p.price - (p.price * s.discountPercentage)/100)" +
            "FROM Sale As s " +
            "   JOIN s.car AS c " +
            "   JOIN s.customer AS cus " +
            "   JOIN c.parts AS p " +
            "GROUP BY cus " +
            "HAVING COUNT (c) > 0")
    List<Object[]> findAllCustomersWithTheCountOfCarsTheyBoughtAndTheMoneySpent();
}
