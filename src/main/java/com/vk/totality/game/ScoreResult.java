package com.vk.totality.game;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ScoreResult {

    private Integer team1ResultScore, team2ResultScore;

    public Integer getTeam1ResultScore() {
        return team1ResultScore;
    }

    public void setTeam1ResultScore(Integer team1ResultScore) {
        this.team1ResultScore = team1ResultScore;
    }

    public Integer getTeam2ResultScore() {
        return team2ResultScore;
    }

    public void setTeam2ResultScore(Integer team2ResultScore) {
        this.team2ResultScore = team2ResultScore;
    }


    public boolean valid() {
        return getTeam1ResultScore() != null && getTeam2ResultScore() != null && getTeam1ResultScore() > -1 && getTeam1ResultScore() > -1;
    }
}
