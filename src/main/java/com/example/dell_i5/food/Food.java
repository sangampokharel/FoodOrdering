package com.example.dell_i5.food;

public class Food {
    public String image,desc,fname,price;

    public Food() {
    }

    public Food(String image, String desc, String fname, String price) {
        this.image = image;
        this.desc = desc;
        this.fname = fname;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
