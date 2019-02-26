package com.example.kimseolki.refrigerator_acin.model;

/**
 * Created by kimseolki on 2017-05-03.
 */

public class PFood {
    public  Integer food_number;
    public  String food_type;
    public  String food_name;
    public  String food_purchase;
    public  String food_exdate;
    public  String food_location;
    public  String food_image;

    public PFood(String food_type, String food_name, String food_purcharse, String food_exdate, String food_location, String food_image) {
        this.food_type = food_type;
        this.food_name = food_name;
        this.food_purchase = food_purcharse;
        this.food_exdate = food_exdate;
        this.food_image = food_image;
        this.food_location = food_location;
    }

    public Integer getFood_number() {
        return food_number;
    }
}
