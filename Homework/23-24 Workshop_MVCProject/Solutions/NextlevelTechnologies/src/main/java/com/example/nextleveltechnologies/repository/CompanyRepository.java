package com.example.nextleveltechnologies.repository;

import com.example.nextleveltechnologies.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsAllBy();

    Optional<Company> findCompanyByName(String name);
}
