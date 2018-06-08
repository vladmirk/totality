package com.vk.totality.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Tournament {
    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3, message = "Название должно быть длиннее")
    private String name;
    private boolean active;

    public Tournament() {
        setActive(true);
    }

    public Tournament(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
