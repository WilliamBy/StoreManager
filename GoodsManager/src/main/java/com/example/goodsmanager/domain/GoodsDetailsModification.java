package com.example.goodsmanager.domain;

import java.io.Serializable;

public class GoodsDetailsModification extends GoodsDetails implements Serializable {
    private boolean modify;
    private String old_name;

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }
}
