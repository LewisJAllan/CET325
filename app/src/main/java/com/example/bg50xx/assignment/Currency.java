package com.example.bg50xx.assignment;

import java.io.Serializable;

/**
 * Created by bg50xx on 14/12/2017.
 */

public class Currency implements Serializable {

    //class to store the rate pulled from the JSON file or predefined on no connection
    //variable
    private float rate;
    //getter and setter
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
