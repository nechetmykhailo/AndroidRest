package com.example.mixazp.androidrest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewRequest {

    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("text")
    @Expose
    private String text;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
