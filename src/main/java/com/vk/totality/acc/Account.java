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
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private AccOperation accOperation;
    private Date eventDate;

    public Account() {
    }

    public Account(UserTournament userTournament, BigDecimal amount, AccOperation accOperation, Date eventDate) {
        this.userTournament = userTournament;
        this.amount = amount;
        this.accOperation = accOperation;
        this.eventDate = eventDate;
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
    }

    public AccOperation getAccOperation() {
        return accOperation;
    }

    public void setAccOperation(AccOperation accOperation) {
        this.accOperation = accOperation;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
