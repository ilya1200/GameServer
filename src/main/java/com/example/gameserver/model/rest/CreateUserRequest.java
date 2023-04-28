package com.example.gameserver.model.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateUserRequest {
    @NotNull(message = "The username is required.")
    @NotEmpty(message = "The username is required.")
    private String username;
    @NotNull(message = "The password is required.")
    @NotEmpty(message = "The password is required.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
