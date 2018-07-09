package com.vk.totality.acc;

import java.math.BigDecimal;

public class TournamentBalance {

    public BigDecimal toBePaidOut = BigDecimal.ZERO;
    public BigDecimal toGetMoney = BigDecimal.ZERO;
    public BigDecimal actual = BigDecimal.ZERO;


    public void addToBePaid(BigDecimal bePaid) {
        toBePaidOut = getToBePaidOut().add(bePaid);
    }

    public void addGetMoney(BigDecimal getMoney) {
        toGetMoney = getToGetMoney().add(getMoney);
    }

    public BigDecimal getToBePaidOut() {
        return toBePaidOut;
    }

    public void addActual(BigDecimal actual) {
        this.actual = getActual().add(actual);
    }

    public BigDecimal getActual() {
        return actual;
    }

    public BigDecimal getToGetMoney() {
        return toGetMoney;
    }

    public BigDecimal getBank() {
        return getActual().add(getToGetMoney()).subtract(getToBePaidOut());
    }
}
