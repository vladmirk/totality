package com.vk.totality.acc;

import java.math.BigDecimal;

public class TournamentResultItem {
    private String userName;
    private BigDecimal winAmount, cost;

    public TournamentResultItem() {
    }

    public TournamentResultItem(String userName, BigDecimal winAmount, BigDecimal cost) {
        this.userName = userName;
        this.winAmount = winAmount;
        this.cost = cost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal balance() {
        return winAmount.subtract(cost);
    }
}
