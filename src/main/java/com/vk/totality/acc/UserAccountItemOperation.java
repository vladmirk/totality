package com.vk.totality.acc;

import com.vk.totality.game.UserTournament;
import com.vk.totality.user.User;

import java.math.BigDecimal;

public class UserAccountItemOperation {
    private UserTournament userTournament;
    private AccOperation accOperation;
    private BigDecimal amount;


    public UserAccountItemOperation() {
    }

    public UserAccountItemOperation(UserTournament userTournament, AccOperation accOperation, BigDecimal amount) {
        this.userTournament = userTournament;
        this.accOperation = accOperation;
        this.amount = amount;
    }

    public User getUser() {
        return userTournament.getUser();
    }


    public AccOperation getAccOperation() {
        return accOperation;
    }

    public void setAccOperation(AccOperation accOperation) {
        this.accOperation = accOperation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UserTournament getUserTournament() {
        return userTournament;
    }
}
