package com.example.goodsmanager.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String passwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return username + ": " + passwd;
    }
}
