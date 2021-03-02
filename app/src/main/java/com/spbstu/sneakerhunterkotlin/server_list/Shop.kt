package com.spbstu.sneakerhunterkotlin.server_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Shop {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}