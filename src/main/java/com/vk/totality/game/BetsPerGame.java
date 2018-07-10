package com.vk.totality.game;

public class BetsPerGame {
    private Game game;
    private Long qnty;


    public BetsPerGame(Game game, Long qnty) {
        this.game = game;
        this.qnty = qnty;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getQnty() {
        return qnty;
    }

    public void setQnty(Long qnty) {
        this.qnty = qnty;
    }
}
