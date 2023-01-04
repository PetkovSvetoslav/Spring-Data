package com.example.automappingobjects.init;

import com.example.automappingobjects.dto.EmployeeDto;
import com.example.automappingobjects.dto.ManagerDto;
import com.example.automappingobjects.entity.Address;
import com.example.automappingobjects.entity.Employee;
import com.example.automappingobjects.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppInitializer implements CommandLineRunner {
    private final EmployeeService employeeService;

    @Autowired
    public AppInitializer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {

        // 1. Simple Mapping
        this.testEx1();

        // 2. Advance Mapping
        this.testEx2();

        // 3. Projection
        this.testEx3();
    }

    private void testEx1() {
        Address address = new Address("Bulgaria", "Sofia", "some random street");
        Employee employee = new Employee("Angel", "Velkov", 420.69, LocalDate.now(), address);

        this.employeeService.save(employee);

        EmployeeDto dto = this.employeeService.findEmployeeAsView(1L);

        System.out.println(dto);
    }

    private void testEx2() {
        Address commonAddress = new Address("Pakistan", "Islamabad", "the left street");

        Employee manager = new Employee("Leonardo", "DiCaprio", 9_999.99, LocalDate.now(), commonAddress);

        int count = 10;

        for (int i = 1; i <= count; i++) {
            Employee e = new Employee("f_name" + i, "l_name" + i,
                    999.99 * i, LocalDate.now(), commonAddress);

            manager.addSubordinate(e);
        }

        this.employeeService.save(manager);

        ManagerDto managerDto = this.employeeService.findManagerAsView(2L);

        System.out.println(managerDto);

        this.employeeService.findAllManagersAsViews().forEach(System.out::println);
    }

    private void testEx3() {
        Address commonAddress = new Address("Bulgaria", "Plovdiv", "hodi na ...");

        Employee manager = new Employee("Max", "Schreck", 9_999.99, LocalDate.now(), new Address());

        this.employeeService.save(manager);

        int count = 10;
        List<Employee> employees = new ArrayList<>(count);

        for (int i = 1; i <= count; i++) {
            Employee e = new Employee("Peter", "Marinov", 999.99 * i / 2,
                    LocalDate.of(1985 + i, 1, 1), commonAddress);

            if (isEven(i)) {
                e.setManager(manager);
            }
            employees.add(e);
        }

        this.employeeService.saveAll(employees);

        this.employeeService.findAllBornBefore(1990).forEach(System.out::println);
    }

    private boolean isEven(int num) {
        return ((num & 1) != 1);
    }
}
