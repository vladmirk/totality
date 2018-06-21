package com.vk.totality.acc;

import com.vk.totality.game.Game;
import com.vk.totality.game.UserTournament;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bet {

    @Id
    @GeneratedValue
    private Long id;

    //        @DateTimeFormat(pattern = "dd.MM.yy HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date betDate;

    private Integer score1, score2;

    @ManyToOne
    private UserTournament userTournament;

    @OneToOne
    private Game game;

    public Bet() {
    }

    public Bet(UserTournament userTournament, Game game, Date betDate) {
        this.betDate = betDate;
        this.userTournament = userTournament;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBetDate() {
        return betDate;
    }

    public void setBetDate(Date betDate) {
        this.betDate = betDate;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public UserTournament getUserTournament() {
        return userTournament;
    }

    public void setUserTournament(UserTournament userTournament) {
        this.userTournament = userTournament;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        if (getScore1() == null || getScore2() == null)
            return "-";

        return getScore1() + ":" + getScore2();
    }
}
