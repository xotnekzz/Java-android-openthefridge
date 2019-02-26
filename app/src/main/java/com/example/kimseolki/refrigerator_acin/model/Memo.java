package com.example.kimseolki.refrigerator_acin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kimseolki on 2017-05-20.

 */

public class Memo {
    @SerializedName("id")
    @Expose
    private Integer id;
/*
    @SerializedName("title")
    @Expose
    private String title;*/

    @SerializedName("memo")
    @Expose
    private String memo;

    @SerializedName("author")
    @Expose
    private String author;

   /* @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("modify_at")
    @Expose
    private String modify_at;*/



    public Memo (String author,String memo){
   //     this.title = title;
        this.author = author;
        this.memo = memo;
  //      this.created_at = created_at;
  //      this.modify_at = modify_at;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  /*  public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/

    public String getMemo() {
        return memo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }



  /*  public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getModify_at() {
        return modify_at;
    }

    public void setModify_at(String modify_at) {
        this.modify_at = modify_at;
    }*/


}
