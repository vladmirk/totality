package com.vk.totality.acc;

import static com.vk.totality.acc.AccOperation.type.C;
import static com.vk.totality.acc.AccOperation.type.D;

public enum
AccOperation {
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

    public enum type {
        D("Replenishment"), C("Extraction");
        private String desc;

        type(String desc) {
            this.desc = desc;
        }

    }
}
