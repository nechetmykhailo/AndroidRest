package com.example.mixazp.androidrest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinalProduct {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product")
    @Expose
    private Integer product;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("text")
    @Expose
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

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
