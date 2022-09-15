package com.example.goodsmanager.domain;

import java.io.Serializable;

public class GoodsCount extends SimpleState implements Serializable {
    private int count;

    public GoodsCount() {
    }

    public GoodsCount(int state, int count) {
        super(state);
        this.count = count;
    }

    public GoodsCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
