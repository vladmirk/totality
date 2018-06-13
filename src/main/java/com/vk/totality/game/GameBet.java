package com.vk.totality.game;

import com.vk.totality.acc.Bet;

import java.util.Date;

public class GameBet {
    private Bet bet;
    private Game game;

    public GameBet(Bet bet, Game game) {
        this.bet = bet;
        this.game = game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Game getGame() {
        return game;
    }

    public Boolean getCanBet() {
        if (getGame() == null || getGame().getStartDate() == null)
            return false;

        Date now = new Date();
        Date game = getGame().getStartDate();
        return now.compareTo(game) < 0;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
