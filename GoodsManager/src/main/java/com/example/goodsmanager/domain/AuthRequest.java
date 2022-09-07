package com.example.goodsmanager.domain;

import java.io.Serializable;

public class AuthRequest extends User implements Serializable {
    private boolean login;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }


}
