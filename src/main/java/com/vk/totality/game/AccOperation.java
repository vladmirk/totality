package com.vk.totality.game;

import static com.vk.totality.game.AccOperation.type.C;
import static com.vk.totality.game.AccOperation.type.D;

public enum AccOperation {
    CASH_IN(D), CASH_OUT(C), BET_WIN(D), BET_OUT(C);

    private type debitCredit;

    private AccOperation(type dc) {
        this.debitCredit = dc;
    }

    public type getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(type debitCredit) {
        this.debitCredit = debitCredit;
    }


    ////////////////////

    enum type {
        D("Replenishment"), C("Extraction");
        private String desc;

        type(String desc) {
            this.desc = desc;
        }

    }
}
