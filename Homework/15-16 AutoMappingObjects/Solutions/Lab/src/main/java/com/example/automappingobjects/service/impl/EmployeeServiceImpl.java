package com.example.automappingobjects.service.impl;

import com.example.automappingobjects.dto.EmployeeDto;
import com.example.automappingobjects.dto.EmployeeWithManagerDto;
import com.example.automappingobjects.dto.ManagerDto;
import com.example.automappingobjects.entity.Employee;
import com.example.automappingobjects.repository.EmployeeRepository;
import com.example.automappingobjects.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Employee employee) {
        this.employeeRepository.save(employee);
    }

    public EmployeeDto findEmployeeAsView(long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow();

        return this.mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public ManagerDto findManagerAsView(long id) {
        Employee manager = this.employeeRepository.findById(id).orElseThrow();

        TypeMap<Employee, ManagerDto> typeMap = this.mapper.createTypeMap(Employee.class, ManagerDto.class);

        typeMap.addMapping(Employee::getSubordinates, ManagerDto::setSubordinates);

        return typeMap.map(manager);
    }

    // List Mapping
    @Override
    public List<ManagerDto> findAllManagersAsViews() {
        return this.mapper.map(
                this.employeeRepository
                        .findAll()
                        .stream()
                        .filter(e -> e.getSubordinates() != null && !e.getSubordinates().isEmpty())
                        .collect(Collectors.toList()),
                new TypeToken<List<ManagerDto>>() {
                }.getType()
        );
    }

    @Override
    public void saveAll(Iterable<Employee> employees) {
        this.employeeRepository.saveAll(employees);
    }

    @Override
    public List<EmployeeWithManagerDto> findAllBornBefore(int year) {
        List<Employee> employees = this.employeeRepository
                .findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate.of(year, 1, 1));

        return employees
                .stream()
                .map(e -> this.mapper.map(e, EmployeeWithManagerDto.class))
                .collect(Collectors.toList());
    }
}
