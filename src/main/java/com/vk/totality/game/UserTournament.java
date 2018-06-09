package com.vk.totality.game;

import com.vk.totality.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserTournament {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Tournament tournament;
    @OneToOne
    private User user;

    private boolean active = true;

    public UserTournament(User user, Tournament tournament, boolean active) {
        this.tournament = tournament;
        this.user = user;
        this.active = active;
    }

    public UserTournament() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
