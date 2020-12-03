package com.spbstu.sneakerhunter;

public class Element  implements Comparable<Element> {
    private String imageURL;
    private String name;
    private int price;

    Element(String imageURL, String name, int price) {
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String  getPriceString() {
        return Long.toString(price) + " руб.";
    }

    @Override
    public String toString() {
        return "Element{" +
                "imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public int compareTo(Element o) {
        return this.getPrice() - o.getPrice();
    }
}