package football_betting_database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "players")
public class Player {
    private long id;
    private String name;
    private byte squadNumber;
    private Team team;
    private Position position;
    private boolean isInjured;

    public Player() {
    }

    public Player(String name, byte squadNumber, Team team, Position position, boolean isInjured) {
        this.name = name;
        this.squadNumber = squadNumber;
        this.team = team;
        this.position = position;
        this.isInjured = isInjured;
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

    @Column(name = "squd_number")
    public byte getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(byte squadNumber) {
        this.squadNumber = squadNumber;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @ManyToOne
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Column(name = "is_injured")
    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id
                && squadNumber == player.squadNumber
                && isInjured == player.isInjured
                && Objects.equals(name, player.name)
                && Objects.equals(team, player.team)
                && Objects.equals(position, player.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, squadNumber, team, position, isInjured);
    }
}