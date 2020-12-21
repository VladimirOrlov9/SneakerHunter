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
        double size1 = Double.parseDouble(this.getSize().substring(3));
        double size2 = Double.parseDouble(o.getSize().substring(3));

        if (size1 < size2) return -1;
        if (size1 > size2) return 1;
        return 0;
    }

}