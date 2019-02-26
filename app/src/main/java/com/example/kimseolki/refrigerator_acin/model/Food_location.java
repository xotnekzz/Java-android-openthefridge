package com.example.kimseolki.refrigerator_acin.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017-05-04.
 */

public class Food_location implements Comparable<Food_location> {

    @SerializedName("food_name")
    @Expose
    private String food_Name;

    @SerializedName("food_purchase")
    @Expose
    private String food_purchase;

    @SerializedName("food_exdate")
    @Expose
    private String food_exdate;

    @SerializedName("food_image")
    @Expose
    private String food_Image;

    @SerializedName("type")
    @Expose
    private String type;

    private int d_day;

    public Food_location(String food_Name, String food_Exdate, String food_Image,int d_day, String type) {
        this.food_Name = food_Name;
      //  this.food_exdate = food_Exdate;
        this.food_Image = food_Image;
        this.d_day = d_day;
        this.type = type;
    }

    public Food_location(String food_Name, int d_day, String food_purchase) {
        this.food_Name = food_Name;
        this.d_day = d_day;
        this.food_purchase = food_purchase;
    }

    public Food_location(String food_Name, int d_day) {
        this.food_Name = food_Name;
        this.d_day = d_day;
    }

    public int getD_day() {
        return d_day;
    }

    public void setD_day(int d_day) {
        this.d_day = d_day;
    }

    public String getFood_Name() {
        return food_Name;
    }

    public void setFood_Name(String food_Name) {
        this.food_Name = food_Name;
    }

    public String getFood_Image() {
        return food_Image;
    }

    public void setFood_Image(String food_Image) {
        this.food_Image = food_Image;
    }

    public String getFood_purchase() {
        return food_purchase;
    }

    public void setFood_purchase(String food_purchase) {
        this.food_purchase = food_purchase;
    }

    public String getFood_Exdate() {
        return food_exdate;
    }

    public void setFood_Exdate(String food_Exdate) {
        this.food_exdate = food_Exdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull Food_location o) {
        if (d_day > o.getD_day()){
            return 1;
        } else if (d_day < o.getD_day()){
            return  - 1;
        }
        return 0;
    }
}
