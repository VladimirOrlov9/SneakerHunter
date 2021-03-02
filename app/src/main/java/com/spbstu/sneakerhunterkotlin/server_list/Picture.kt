package com.spbstu.sneakerhunterkotlin.server_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Picture {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}