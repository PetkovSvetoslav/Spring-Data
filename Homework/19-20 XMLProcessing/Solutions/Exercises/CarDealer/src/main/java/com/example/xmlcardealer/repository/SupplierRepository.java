package com.example.xmlcardealer.repository;

import com.example.xmlcardealer.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "SELECT s.id, s.name, COUNT(p.id) " +
            "FROM suppliers AS s " +
            "JOIN parts AS p ON s.id = p.supplier_id " +
            "WHERE NOT s.is_importer " +
            "GROUP BY s.id", nativeQuery = true)
    List<Object[]> findAllLocalSupplierWithTheNumberOfPartsTheySupply();
}
