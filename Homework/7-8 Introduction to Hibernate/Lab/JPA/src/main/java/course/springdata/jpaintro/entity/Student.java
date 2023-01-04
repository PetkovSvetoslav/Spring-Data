package course.springdata.jpaintro.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "registration_date")
    private Date registrationDate;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
        this.registrationDate = new Date();
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