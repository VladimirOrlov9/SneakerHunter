package com.spbstu.sneakerhunter.server_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Size implements Comparable<Size> {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("size")
    @Expose
    private String size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int compareTo(Size o) {
        int size1 = Integer.parseInt(this.getSize().substring(3));
        int size2 = Integer.parseInt(o.getSize().substring(3));
        return size1 - size2;
    }

}