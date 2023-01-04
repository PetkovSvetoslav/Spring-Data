package university_system;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher extends User {
    private String email;
    private Double salaryPerHour;
    private Course course;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String phoneNumber, String email, Double salaryPerHour, Course course) {
        super(firstName, lastName, phoneNumber);
        this.email = email;
        this.salaryPerHour = salaryPerHour;
        this.course = course;
    }

    @Column(length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour")
    public Double getSalaryPerHour() {
        return this.salaryPerHour;
    }

    public void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() +
                "   email = " + email + System.lineSeparator() +
                "   salaryPerHour=" + salaryPerHour;
    }
}