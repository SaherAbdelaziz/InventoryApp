package com.inventoryapp.inventoryapp;

import java.io.Serializable;

/**
 * Created by SaherOs on 3/1/2018.
 */

public class MyProduct implements Serializable {
    private int quantity;
    private int price;
    private String image;
    private String supplierMail;
    private int myId;
    private String name;


    public MyProduct() {
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSupplierMail(String supplierMail) {
        this.supplierMail = supplierMail;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getSupplierMail() {
        return supplierMail;
    }


    public int getMyId() {return myId;}

    public String getName() {
        return name;
    }

}

   