package hospital_database;

import javax.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    private long id;
    private String name;
    private String comments;
    private Patient patient;

    public Diagnose() {
    }

    public Diagnose(String name, String comment, Patient patient) {
        this.name = name;
        this.comments = comment;
        this.patient = patient;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "TEXT")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @ManyToOne
    @JoinColumn
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}