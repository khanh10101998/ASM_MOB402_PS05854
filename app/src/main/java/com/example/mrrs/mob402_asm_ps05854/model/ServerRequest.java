package com.example.mrrs.mob402_asm_ps05854.model;

public class ServerRequest {
    private String operation;
    private User user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}