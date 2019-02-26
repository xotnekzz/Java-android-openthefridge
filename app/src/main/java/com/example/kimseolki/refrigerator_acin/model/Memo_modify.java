package com.example.kimseolki.refrigerator_acin.model;

import java.io.Serializable;

/**
 * Created by kimseolki on 2017-05-25.
 */

public class Memo_modify implements Serializable {
    public Integer id;

    private String author;

    private String memo;

    public Memo_modify(Integer id, String author,String memo){
        this.id = id;
        this.author = author;
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
