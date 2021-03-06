package com.vk.totality.acc;

import com.vk.totality.game.UserTournament;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserTournament userTournament;
    //    @NotNull
//    @NotBlank
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private AccOperation accOperation;
    private Date eventDate;

    @ManyToOne
    private BetResultItem betResultItem;

    public Account() {
    }

    public Account(UserTournament userTournament, BigDecimal amount, AccOperation accOperation, Date eventDate) {
        this.userTournament = userTournament;
        setAmount(amount);
        setAccOperation(accOperation);
        this.eventDate = eventDate;
    }

    public Account(UserTournament userTournament, BigDecimal amount, AccOperation accOperation, Date eventDate, BetResultItem betResultItem) {
        this(userTournament, amount, accOperation, eventDate);
        this.betResultItem = betResultItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTournament getUserTournament() {
        return userTournament;
    }

    public void setUserTournament(UserTournament userTournament) {
        this.userTournament = userTournament;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        validate();
    }

    public AccOperation getAccOperation() {
        return accOperation;
    }

    public void setAccOperation(AccOperation accOperation) {
        this.accOperation = accOperation;
        validate();
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    private void validate() {
        if (getAccOperation() == null || getAmount() == null) return;

        if (AccOperation.type.D.equals(getAccOperation().getDebitCredit()))
            amount = getAmount().abs();
        else
            amount = getAmount().abs().negate();
    }

    public BetResultItem getBetResultItem() {
        return betResultItem;
    }
}
