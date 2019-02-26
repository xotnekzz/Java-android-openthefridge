package com.example.kimseolki.refrigerator_acin;

import com.example.kimseolki.refrigerator_acin.model.Food_location;

import java.util.ArrayList;

/**
 * Created by 6201P-03 on 2017-05-29.
 */

public class FoodInfo {
    private ArrayList<Food_location> food_location = new ArrayList<Food_location>();
    private int food_type;
    private int type_name;
    private String pre_image;


    public String getPre_image() {
        return pre_image;
    }

    public void setPre_image(String pre_image) {
        this.pre_image = pre_image;
    }

    private int settingdate;
    private int exdate_total;
    private static FoodInfo instance = new FoodInfo();

    public FoodInfo(String s){}

    public FoodInfo(){

    }

    public int getType_name() {
        return type_name;
    }

    public void setType_name(int type_name) {
        this.type_name = type_name;
    }

    public static FoodInfo getInstance(){
        return instance;
    }

    public int getFood_type() {
        return food_type;
    }

    public void setFood_type(int food_type) {
        this.food_type = food_type;
    }

    public ArrayList<Food_location> getFood_location() {
        return food_location;
    }

    public void setFood_location(ArrayList<Food_location> food_location) {
        this.food_location = food_location;
    }
    public int getExdate_total() {
        return exdate_total;
    }

    public void setExdate_total(int exdate_total) {
        this.exdate_total = exdate_total;
    }

    public int getSettingdate() {
        return settingdate;
    }

    public void setSettingdate(int settingdate) {
        this.settingdate = settingdate;
    }


}
