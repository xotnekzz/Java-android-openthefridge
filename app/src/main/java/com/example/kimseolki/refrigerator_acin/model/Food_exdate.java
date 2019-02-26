package com.example.kimseolki.refrigerator_acin.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017-05-04.
 */

public class Food_exdate implements Comparable<Food_exdate> {

    @SerializedName("food_name")
    @Expose
    private String food_Name;

    private int d_day;

    public Food_exdate(String food_Name, int d_day) {
        this.food_Name = food_Name;
        this.d_day = d_day;
    }

    public int getD_day() {
        return d_day;
    }

    public void setD_day(int d_day)    {
        this.d_day = d_day;
    }

    public String getFood_Name() {
        return food_Name;
    }

    public void setFood_Name(String food_Name) {
        this.food_Name = food_Name;
    }


    @Override
    public int compareTo(@NonNull Food_exdate o) {
        if (d_day > o.getD_day()){
            return 1;
        } else if (d_day < o.getD_day()){
            return  -1;
        }
        return 0;
    }
}
