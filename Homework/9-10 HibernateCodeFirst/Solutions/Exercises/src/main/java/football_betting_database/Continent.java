package football_betting_database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "continets")
public class Continent {
    private byte id;
    private String name;
    private Set<Country> countries;

    public Continent() {
    }

    public Continent(String name) {
        this.name = name;
        this.countries = new HashSet<>();
    }

    public Continent(String name, Set<Country> countries) {
        this(name);
        this.countries = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    @Column(nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "continent")
    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return id == continent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}