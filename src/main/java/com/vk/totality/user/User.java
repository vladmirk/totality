package com.vk.totality.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String userLogin;
    private String userPassword;
    private String[] roles;
    private Boolean active;

    @Transient
    public final static String EMPTY_PASS = "empty";


    public User() {
    }

    public User(String username, String userPassword, Boolean active, String... roles) {
        this.userLogin = username;
        this.userPassword = userPassword;
        this.active = active;
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


}
