package com.vk.totality.game;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "dd.MM.yy HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @OneToOne
    private Team team1;

    @OneToOne
    private Team team2;

    @ManyToOne
    private Tournament tournament;

    private BigDecimal rate;

    public Game() {
    }

    public Game(Tournament tournament, Date startDate, Team team1, Team team2, BigDecimal rate) {
        this.tournament = tournament;
        this.startDate = startDate;
        this.team1 = team1;
        this.team2 = team2;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
