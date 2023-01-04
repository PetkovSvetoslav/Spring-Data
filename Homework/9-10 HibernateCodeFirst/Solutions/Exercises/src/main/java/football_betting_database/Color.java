package football_betting_database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "colors")
public class Color {
    private long id;
    private String name;

    public Color() {
    }

    public Color(String name) {
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

    @Column(nullable = false, length = 20)
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
        Color color = (Color) o;
        return id == color.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}