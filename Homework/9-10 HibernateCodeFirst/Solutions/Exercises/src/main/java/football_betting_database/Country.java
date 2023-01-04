package football_betting_database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class Country {
    private String id;
    private String name;
    private Continent continent;

    public Country() {
    }

    public Country(String id, String name, Continent continent) {
        this.setId(id);
        this.name = name;
        this.continent = continent;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.trim().length() != 3) {
            throw new IllegalArgumentException("Id must be 3 letters");
        }

        StringBuilder capitals = new StringBuilder();
        for (int i = 0; i < id.length(); i++) {
            capitals.append(String.valueOf(id.charAt(i)).toUpperCase());
        }

        this.id = capitals.toString();
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "continent_id", referencedColumnName = "id")
    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id.equals(country.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}