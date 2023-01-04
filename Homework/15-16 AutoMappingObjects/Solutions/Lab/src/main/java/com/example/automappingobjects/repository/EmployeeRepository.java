package com.example.automappingobjects.repository;

import com.example.automappingobjects.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends BaseRepository<Employee> {

    List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate birthday);
}
