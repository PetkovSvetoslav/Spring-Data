package com.example.automappingobjectsexercise.repository;

import com.example.automappingobjectsexercise.model.entity.Order;
import com.example.automappingobjectsexercise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByBuyerAndBoughtFalse(User user);
}
