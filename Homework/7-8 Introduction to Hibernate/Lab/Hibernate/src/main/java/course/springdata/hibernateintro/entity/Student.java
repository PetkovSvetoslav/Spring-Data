package course.springdata.hibernateintro.entity;

import java.util.Date;
import java.util.Objects;

public class Student {
    private long id;
    private String name;
    private Date registrationDate;
    private Address address;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
        this.registrationDate = new Date();
    }

    public Student(String name, Address address) {
        this(name);
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student {" + System.lineSeparator() +
                "   id = " + id + System.lineSeparator() +
                "   name = " + name + System.lineSeparator() +
                "   registrationDate = " + registrationDate + System.lineSeparator() +
                '}';
    }
}