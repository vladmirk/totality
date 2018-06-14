package com.vk.totality.acc;

import java.math.BigDecimal;

public class AccBalance {
    private BigDecimal balanceAmount;

    public AccBalance() {
    }

    public AccBalance(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
