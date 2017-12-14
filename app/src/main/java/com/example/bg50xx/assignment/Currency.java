package com.example.bg50xx.assignment;

import java.io.Serializable;

/**
 * Created by bg50xx on 14/12/2017.
 */

public class Currency implements Serializable {

    private float rate;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
