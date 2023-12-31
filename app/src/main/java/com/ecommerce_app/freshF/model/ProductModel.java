package com.ecommerce_app.freshF.model;

import java.io.Serializable;

public class ProductModel implements Serializable {

    String id;
    String name;
    String description;
    String img_url;

    String price;

    String type;

    public ProductModel(){

    }

    public ProductModel(String name, String description, String img_url,String price,String type) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
