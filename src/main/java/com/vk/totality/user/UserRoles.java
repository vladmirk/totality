package com.vk.totality.user;

public enum UserRoles {
    USER,
    ADMIN;


    @Override
    public String toString() {
        return "ROLE_" + super.toString();
    }
}
