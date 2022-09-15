package com.example.goodsmanager.domain;

import java.io.Serializable;

public class UserAuthentication extends SimpleState implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
