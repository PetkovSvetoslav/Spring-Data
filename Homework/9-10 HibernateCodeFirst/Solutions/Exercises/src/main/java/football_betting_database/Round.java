package football_betting_database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rounds")
public class Round {
    private long id;
    private String name;

    public Round() {
    }

    public Round(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id
                && Objects.equals(name, round.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}