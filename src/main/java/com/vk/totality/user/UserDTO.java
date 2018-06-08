package com.vk.totality.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull
    @Size(message = "Слишком короткий логин.", min = 3, max = 20)
    private String userLogin;
    @NotNull(message = "Введите пароль")
    @Size(message = "Слишком короткий пароль.", min = 3, max = 20)
    private String userPassword1;
    @NotNull(message = "Введите поддтверждение пароля")
    private String userPassword2;
    private Boolean admin;
    private Boolean active;


    public UserDTO() {
        setActive(false);
        setAdmin(true);
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUserPassword1() {
        return userPassword1;
    }

    public void setUserPassword1(String userPassword1) {
        this.userPassword1 = userPassword1;
    }

    public String getUserPassword2() {
        return userPassword2;
    }

    public void setUserPassword2(String userPassword2) {
        this.userPassword2 = userPassword2;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
