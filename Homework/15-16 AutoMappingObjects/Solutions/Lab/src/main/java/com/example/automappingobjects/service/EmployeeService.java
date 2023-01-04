package com.example.automappingobjects.service;

import com.example.automappingobjects.dto.EmployeeDto;
import com.example.automappingobjects.dto.EmployeeWithManagerDto;
import com.example.automappingobjects.dto.ManagerDto;
import com.example.automappingobjects.entity.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);

    EmployeeDto findEmployeeAsView(long id);

    ManagerDto findManagerAsView(long id);

    List<ManagerDto> findAllManagersAsViews();

    void saveAll(Iterable<Employee> employees);

    List<EmployeeWithManagerDto> findAllBornBefore(int year);
}
