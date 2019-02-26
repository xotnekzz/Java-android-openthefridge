package com.example.kimseolki.refrigerator_acin.model;

import java.io.Serializable;

/**
 * Created by pc on 2017-05-04.
 */
@SuppressWarnings("food_modify")
public class Food_modify implements Serializable{

    public   Integer food_number;

    public Food_modify(Integer food_number, String food_type, String food_name, String food_exdate, String food_purchase, String food_location, String food_image) {
        this.food_number = food_number;
        this.food_type = food_type;
        this.food_name = food_name;
        this.food_purchase = food_purchase;
        this.food_exdate = food_exdate;
        this.food_location = food_location;
        this.food_image = food_image;
    }

    public  String food_type;
    public  String food_name;
    public  String food_purchase;
    public  String food_exdate;
    public  String food_location;
    public  String food_image;

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public Integer getFood_number() {
        return food_number;
    }

    public void setFood_number(Integer food_number) {
        this.food_number = food_number;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_purchase() {
        return food_purchase;
    }

    public void setFood_purchase(String food_purchase) {
        this.food_purchase = food_purchase;
    }

    public String getFood_exdate() {
        return food_exdate;
    }

    public void setFood_exdate(String food_exdate) {
        this.food_exdate = food_exdate;
    }

    public String getFood_location() {
        return food_location;
    }

    public void setFood_location(String food_location) {
        this.food_location = food_location;
    }
}
