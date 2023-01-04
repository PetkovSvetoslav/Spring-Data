package football_betting_database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "positions")
public class Position {
    private String id;
    private String positionDescription;

    public Position() {
    }

    public Position(String id, String positionDescription) {
        this.setId(id);
        this.positionDescription = positionDescription;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.trim().length() != 2) {
            throw new IllegalArgumentException("Id must be 2 letters");
        }

        StringBuilder capitals = new StringBuilder();
        for (int i = 0; i < id.length(); i++) {
            capitals.append(String.valueOf(id.charAt(i)).toUpperCase());
        }

        this.id = capitals.toString();
    }

    @Column(name = "position_description", nullable = false)
    public String getPositionDescription() {
        return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id)
                && Objects.equals(positionDescription, position.positionDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionDescription);
    }
}