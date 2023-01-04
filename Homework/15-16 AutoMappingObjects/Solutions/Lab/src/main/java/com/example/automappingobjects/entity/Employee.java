package com.example.automappingobjects.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private double salary;

    @NonNull
    private LocalDate birthday;

    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;

    private boolean onHoliday;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee manager;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Employee> subordinates = new HashSet<>();

    public void setManager(Employee newManager) {
        if (this.manager != null) {
            this.manager.getSubordinates().remove(this);
        }

        this.manager = newManager;
        newManager.getSubordinates().add(this);
    }

    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
        employee.setManager(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.salary, salary) == 0
                && Objects.equals(id, employee.id)
                && firstName.equals(employee.firstName)
                && lastName.equals(employee.lastName)
                && birthday.equals(employee.birthday)
                && address.equals(employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, salary, birthday, address);
    }
}
