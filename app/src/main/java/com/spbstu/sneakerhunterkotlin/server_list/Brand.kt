package com.spbstu.sneakerhunterkotlin.server_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Brand : Comparable<Brand?> {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    override fun compareTo(other: Brand?): Int {
        return name!!.compareTo(other?.name!!)
    }
}