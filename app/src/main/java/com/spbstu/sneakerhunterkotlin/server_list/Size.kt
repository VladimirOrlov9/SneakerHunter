package com.spbstu.sneakerhunterkotlin.server_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Size : Comparable<Size?> {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("size")
    @Expose
    var size: String? = null

    override fun compareTo(other: Size?): Int {
        val size1 = size!!.substring(3).toDouble()
        val size2 = other?.size!!.substring(3).toDouble()
        if (size1 < size2) return -1
        return if (size1 > size2) 1 else 0
    }
}