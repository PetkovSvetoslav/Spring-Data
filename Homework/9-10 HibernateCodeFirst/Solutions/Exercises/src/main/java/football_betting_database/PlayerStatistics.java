package football_betting_database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "player_statistics")
public class PlayerStatistics implements Serializable {
    private Game game;
    private Player player;
    private int scoredGoals;
    private int assists;
    private short playedMinutes;

    public PlayerStatistics() {
    }

    public PlayerStatistics(Game game, Player player, int scoredGoals, int assists, byte playedMinutes) {
        this.game = game;
        this.player = player;
        this.scoredGoals = scoredGoals;
        this.assists = assists;
        this.playedMinutes = playedMinutes;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Column(name = "scored_goals")
    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Column(name = "played_minutes")
    public short getPlayedMinutes() {
        return playedMinutes;
    }

    public void setPlayedMinutes(short playedMinutes) {
        this.playedMinutes = playedMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatistics that = (PlayerStatistics) o;
        return Objects.equals(game, that.game)
                && Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, player);
    }
}