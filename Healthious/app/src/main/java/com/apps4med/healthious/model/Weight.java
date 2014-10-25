package com.apps4med.healthious.model;

public class Weight {
    public String type;
    public int week;
    public Double weight;

    public Weight(int week, Double weight) {
        this.week = week;
        this.weight = weight;
    }

}
