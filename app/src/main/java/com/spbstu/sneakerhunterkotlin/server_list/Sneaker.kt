package com.spbstu.sneakerhunterkotlin.server_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Sneaker : Comparable<Sneaker?> {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("shop")
    @Expose
    var shop: Shop? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("size")
    @Expose
    var size: List<Size>? = null

    @SerializedName("brand")
    @Expose
    var brand: Brand? = null

    @SerializedName("picture")
    @Expose
    var picture: Picture? = null

    @SerializedName("money")
    @Expose
    var money: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("uri")
    @Expose
    var uri: String? = null

    @SerializedName("customer")
    @Expose
    var customer: Any? = null

    val doubleMoney: Double
        get() {
            val data =
                money!!.substring(money!!.indexOf("$")).replace("[^0-9.]".toRegex(), "")
            return data.toDouble()
        }

    override fun compareTo(other: Sneaker?): Int {
        val size1 =
            money!!.substring(money!!.indexOf("$")).replace("[^0-9.]".toRegex(), "")
                .toDouble()
        val size2 =
            other?.money!!.substring(other.money!!.indexOf("$")).replace("[^0-9.]".toRegex(), "")
                .toDouble()
        if (size1 < size2) return -1
        return if (size1 > size2) 1 else 0
    }
}