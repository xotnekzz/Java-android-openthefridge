package com.example.kimseolki.refrigerator_acin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class Food {
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("food_number")
    @Expose
    private Integer food_number;

    @SerializedName("food_type")
    @Expose
    private String food_type;

    @SerializedName("food_name")
    @Expose
    private String food_name;

    @SerializedName("food_purchase")
    @Expose
    private String food_purchase;

    @SerializedName("food_exdate")
    @Expose
    private String food_exdate;

    @SerializedName("food_location")
    @Expose
    private String food_location;

    @SerializedName("food_image")
    @Expose
    private String food_Image;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public void setFood_purchase(String food_purcharse) {
        this.food_purchase = food_purcharse;
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

    public String getFood_Image() {
        return food_Image;
    }

    public void setFood_Image(String food_Image) {
        this.food_Image = food_Image;
    }
}
