package com.vk.totality.game;

import com.vk.totality.acc.Bet;
import com.vk.totality.acc.BetResultItem;

import java.util.Date;

public class GameBet {
    private Bet bet;
    private Game game;
    private BetResultItem betResultItem;

    public GameBet(Bet bet, Game game, BetResultItem betResultItem) {
        this.bet = bet;
        this.game = game;
        this.betResultItem = betResultItem;
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

    public BetResultItem getBetResultItem() {
        return betResultItem;
    }

    public void setBetResultItem(BetResultItem betResultItem) {
        this.betResultItem = betResultItem;
    }
}
