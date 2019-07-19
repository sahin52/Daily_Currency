package com.sahins.were;
/*
* This package is to keep one type of currency's name and value*/
public class OneCurrency {
    private String name;
    private double rate;
    public OneCurrency(String name, double rate) {
        this.name=name;
        this.rate=rate;
    }

    public double getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }
}
