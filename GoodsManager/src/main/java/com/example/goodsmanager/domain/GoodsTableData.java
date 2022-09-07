package com.example.goodsmanager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* 专门为了Layui数据表响应体定制的域 */
public class GoodsTableData implements Serializable {
    private int code;
    private int count;
    private String msg;
    private List<GoodsDetails> data;

    public GoodsTableData(int code, int count, String msg, List<GoodsDetails> data) {
        this.code = code;
        this.count = count;
        this.msg = msg;
        this.data = data;
    }

    public GoodsTableData() {
        code = 404;
        count = 0;
        msg = "";
        data = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GoodsDetails> getData() {
        return data;
    }

    public void setData(List<GoodsDetails> data) {
        this.data = data;
    }
}
