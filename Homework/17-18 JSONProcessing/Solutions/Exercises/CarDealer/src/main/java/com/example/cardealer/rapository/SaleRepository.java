package com.example.cardealer.rapository;

import com.example.cardealer.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT c.make, c.model, c.travelledDistance, cus.name, " +
            "   s.discountPercentage / 100, SUM(p.price), " +
            "   SUM(p.price) * (1 - s.discountPercentage / 100)" +
            "FROM Sale AS s " +
            "   JOIN s.car AS c " +
            "   JOIN c.parts AS p " +
            "   JOIN s.customer AS cus " +
            "GROUP BY s")
    List<Object[]> findAllSalesAsInfo();
}
