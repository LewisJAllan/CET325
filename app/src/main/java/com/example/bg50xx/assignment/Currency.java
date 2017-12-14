package com.example.bg50xx.assignment;

import java.io.Serializable;

/**
 * Created by bg50xx on 14/12/2017.
 */

public class Currency implements Serializable {
    private String curType;
    private float rate;

    public String getCurType() {
        return curType;
    }

    public void setCurType(String curType) {
        this.curType = curType;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
