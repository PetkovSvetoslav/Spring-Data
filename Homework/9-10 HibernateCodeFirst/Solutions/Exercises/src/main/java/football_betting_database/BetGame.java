package football_betting_database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bet_games")
public class BetGame implements Serializable {
    private Game game;
    private Bet bet;
    private ResultPrediction resultPrediction;

    public BetGame() {
    }

    public BetGame(Game game, Bet bet, ResultPrediction resultPrediction) {
        this.game = game;
        this.bet = bet;
        this.resultPrediction = resultPrediction;
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
    @JoinColumn(name = "bet_id", referencedColumnName = "id")
    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @Enumerated(EnumType.STRING)
    public ResultPrediction getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetGame betGame = (BetGame) o;
        return Objects.equals(game, betGame.game)
                && Objects.equals(bet, betGame.bet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, bet);
    }
}