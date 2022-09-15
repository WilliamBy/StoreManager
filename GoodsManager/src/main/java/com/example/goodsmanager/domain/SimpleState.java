package com.example.goodsmanager.domain;

import java.io.Serializable;

public class SimpleState implements Serializable {
    private int state;

    public SimpleState(int state) {
        this.state = state;
    }

    public SimpleState() {
        this.state = 0;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
