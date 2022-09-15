package com.example.goodsmanager.domain;

import java.io.Serializable;

public class GoodsDetails implements Serializable {
    private String name;
    private int price;
    private int entry_price;
    private String from;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEntry_price() {
        return entry_price;
    }

    public void setEntry_price(int entry_price) {
        this.entry_price = entry_price;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
