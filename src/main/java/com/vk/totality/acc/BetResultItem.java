package com.vk.totality.acc;

import com.vk.totality.game.BetResult;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class BetResultItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private BetResult betResult;

    @OneToOne
    private Bet bet;

    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    private BetResultStatus betResultStatus;

    private BigDecimal winAmount;


    public BetResultItem() {
    }

    public BetResultItem(BetResult betResult, Bet bet) {
        this.betResult = betResult;
        this.bet = bet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BetResult getBetResult() {
        return betResult;
    }

    public void setBetResult(BetResult betResult) {
        this.betResult = betResult;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BetResultStatus getBetResultStatus() {
        return betResultStatus;
    }

    public void setBetResultStatus(BetResultStatus betResultStatus) {
        this.betResultStatus = betResultStatus;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }
}
