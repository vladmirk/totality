package com.vk.totality.acc;

import com.vk.totality.game.UserTournament;
import com.vk.totality.user.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UserAccountOperationSummary {
    private UserTournament userTournament;
    private Map<AccOperation, BigDecimal> accOperationMap = new HashMap<>();


    public UserAccountOperationSummary() {
        initMap();
    }

    private void initMap() {
        for (AccOperation ao : AccOperation.values())
            accOperationMap.put(ao, BigDecimal.ZERO);
    }


    public User getUser() {
        return getUserTournament().getUser();
    }

    public UserTournament getUserTournament() {
        return userTournament;
    }

    public void setUserTournament(UserTournament userTournament) {
        this.userTournament = userTournament;
    }

    public void add(AccOperation accOperation, BigDecimal amount) {
        accOperationMap.put(accOperation, accOperationMap.get(accOperation).add(amount));
    }

    public BigDecimal getAmount(AccOperation accOperation) {
        return accOperationMap.get(accOperation);
    }


    public BigDecimal getJackPot() {
        return getAmount(AccOperation.BET_OUT).add(getAmount(AccOperation.BET_WIN));
    }

    public BigDecimal getCashBalance() {
        return getAmount(AccOperation.CASH_IN).add(getAmount(AccOperation.CASH_OUT));
    }


    public BigDecimal getToBePaidOutAmount() {
        return BigDecimal.ZERO.compareTo(getBalance()) > 0 ? BigDecimal.ZERO : getBalance();
    }

    public BigDecimal getPaymentMissing() {
        return BigDecimal.ZERO.compareTo(getBalance()) < 0 ? BigDecimal.ZERO : getBalance().abs();
    }


    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.ZERO;
        for (AccOperation acc : AccOperation.values()) {
            balance = balance.add(getAmount(acc));
        }
        return balance;

    }
}
