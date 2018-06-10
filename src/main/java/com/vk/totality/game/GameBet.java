package com.vk.totality.game;

import com.vk.totality.acc.Bet;

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

    public void setGame(Game game) {
        this.game = game;
    }
}
