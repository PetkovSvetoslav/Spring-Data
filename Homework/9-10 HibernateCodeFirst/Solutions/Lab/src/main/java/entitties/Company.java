package entitties;

import entitties.vehicles.Airliner;
import entitties.vehicles.Plane;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "companies")
public class Company {
    private long id;
    private String name;
    private Set<Airliner> planes;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
        this.planes = new HashSet<>();
    }

    public Company(String name, Set<Airliner> planes) {
        this(name);
        this.planes = planes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public boolean addPlane(Airliner airliner) {
        return this.planes.add(airliner);
    }

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Airliner> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<Airliner> planes) {
        this.planes = planes;
    }
}