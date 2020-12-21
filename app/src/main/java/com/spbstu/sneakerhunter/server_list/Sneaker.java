package com.spbstu.sneakerhunter.server_list;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sneaker implements Comparable<Sneaker> {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shop")
    @Expose
    private Shop shop;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private List<Size> size = null;
    @SerializedName("brand")
    @Expose
    private Brand brand;
    @SerializedName("picture")
    @Expose
    private Picture picture;
    @SerializedName("money")
    @Expose
    private String money;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("customer")
    @Expose
    private Object customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Object getCustomer() {
        return customer;
    }

    public void setCustomer(Object customer) {
        this.customer = customer;
    }

    @Override
    public int compareTo(Sneaker o) {
        double size1 = Double.parseDouble(this.money.substring(this.money.indexOf("$")).replaceAll("[^0-9.]", ""));
        double size2 = Double.parseDouble(o.getMoney().substring(o.getMoney().indexOf("$")).replaceAll("[^0-9.]", ""));

        if (size1 < size2) return -1;
        if (size1 > size2) return 1;
        return 0;
    }

    public double getDoubleMoney() {
        String data = this.money.substring(money.indexOf("$")).replaceAll("[^0-9.]", "");
        return Double.parseDouble(data);
    }
}