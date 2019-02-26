package com.example.kimseolki.refrigerator_acin.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("cauthor")
    @Expose
    private String cauthor;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("modify_at")
    @Expose
    private String modify_at;

    public String getcAuthor() {
        return cauthor;
    }

    public void setcAuthor(String author) {
        this.cauthor = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated_at() {
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
    }
}
