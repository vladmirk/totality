package com.vk.totality.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    private Date startDate;

    @OneToOne
    private Team team1;

    @OneToOne
    private Team team2;

    private int team1ResultScore = 0;
    private int team2ResultScore = 0;

    public Game() {
    }

    public Game(Date startDate, Team team1, Team team2, int team1ResultScore, int team2ResultScore) {
        this.startDate = startDate;
        this.team1 = team1;
        this.team2 = team2;
        this.team1ResultScore = team1ResultScore;
        this.team2ResultScore = team2ResultScore;
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

    public int getTeam1ResultScore() {
        return team1ResultScore;
    }

    public void setTeam1ResultScore(int team1ResultScore) {
        this.team1ResultScore = team1ResultScore;
    }

    public void setTeam2ResultScore(int team2ResultScore) {
        this.team2ResultScore = team2ResultScore;
    }

    public int getTeam2ResultScore() {
        return team2ResultScore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
