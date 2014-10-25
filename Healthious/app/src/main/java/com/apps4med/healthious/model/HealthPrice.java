package com.apps4med.healthious.model;

/**
 * Created by iskitsas on 26/10/2014.
 */
public class HealthPrice {
    String id;
    String description;
    String hospitalization; //days
    String price; //euros

    public HealthPrice(String id, String description, String hospitalization, String price) {
        this.id = id;
        this.description = description;
        this.hospitalization = hospitalization;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(String hospitalization) {
        this.hospitalization = hospitalization;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
