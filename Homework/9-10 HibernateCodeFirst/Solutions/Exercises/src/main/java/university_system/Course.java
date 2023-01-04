package university_system;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    private long id;
    private String name;
    private String description;
    private Date startSate;
    private Date endDate;
    private int credits;
    private Set<Student> students;
    private Set<Teacher> teachers;

    public Course() {
    }

    public Course(String name, String description, Date startSate, Date endDate, int credits) {
        this.name = name;
        this.description = description;
        this.startSate = startSate;
        this.endDate = endDate;
        this.credits = credits;
        this.students = new LinkedHashSet<>();
        this.teachers = new LinkedHashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "start_date")
    public Date getStartSate() {
        return this.startSate;
    }

    public void setStartSate(Date startSate) {
        this.startSate = startSate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @ManyToMany
    @JoinTable
    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @OneToMany(mappedBy = "course")
    @Column(nullable = false)
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course:" + System.lineSeparator() +
                "id = " + id + System.lineSeparator() +
                "name = " + name + System.lineSeparator() +
                "description = " + description + System.lineSeparator() +
                "startSate = " + startSate + System.lineSeparator() +
                "endDate = " + endDate + System.lineSeparator() +
                "credits = " + credits;
    }
}