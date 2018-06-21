package com.vk.totality.game;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class BetResult extends ScoreResult {

    @Id
    @GeneratedValue
    private Long id;


    @Temporal(TemporalType.TIMESTAMP)
    private Date resultDate;


    @OneToOne
    private Game game;

    private BigDecimal gameBudget;

    BetResult() {
    }

    public BetResult(Game game) {
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public BigDecimal getGameBudget() {
        return gameBudget;
    }

    public void setGameBudget(BigDecimal gameBudget) {
        this.gameBudget = gameBudget;
    }

    public boolean same(ScoreResult scoreResult) {
        return getTeam1ResultScore() != null && getTeam2ResultScore() != null && scoreResult != null
                && getTeam1ResultScore().equals(scoreResult.getTeam1ResultScore()) && getTeam2ResultScore().equals(scoreResult.getTeam2ResultScore());
    }
}
