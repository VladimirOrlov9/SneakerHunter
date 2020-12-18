package com.spbstu.sneakerhunter;

public class Element  implements Comparable<Element> {

    private final int sneakerKey;
    private final String name;
    private final double price;
    private final String brand;
    private final String category;
    private final String color;
    private final String shopIRL;
    private final String imageURL;

    public Element(int sneakerKey, String name, double price, String brand,
                   String category, String color, String shopIRL, String imageURL) {
        this.sneakerKey = sneakerKey;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.shopIRL = shopIRL;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getShopIRL() {
        return shopIRL;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Element{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", brand=" + brand +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", shopIRL='" + shopIRL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    public String  getPriceString() {
        return Double.toString(price) + " руб.";
    }


    @Override
    public int compareTo(Element o) {
        double thisPrice = this.getPrice() * 100;
        double oPrice = o.getPrice() * 100;
        return (int)thisPrice - (int)oPrice;
    }

    public int getSneakerKey() {
        return sneakerKey;
    }
}