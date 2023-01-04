package com.example.nextleveltechnologies.repository;

import com.example.nextleveltechnologies.model.dto.EmployeeViewDto;
import com.example.nextleveltechnologies.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsAllBy();

    @Query("SELECT new com.example.nextleveltechnologies.model.dto.EmployeeViewDto(" +
            "(CONCAT(e.firstName,' ', e.lastName)), e.age, p.name) " +
            "FROM Employee AS e " +
            "   JOIN e.project AS p " +
            "WHERE e.age < :age")
    List<EmployeeViewDto> findAllByAgeAfter(@Param("age") byte age);
}
