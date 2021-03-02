package com.spbstu.sneakerhunterkotlin.server_list

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface SneakersAPI {
    @get:GET("sneakers")
    val sneakers: Call<List<Sneaker>>

    @get:GET("size")
    val sizes: Call<List<Size?>?>?

    @get:GET("brand")
    val brands: Call<List<Brand?>?>?

    @GET("sneakers/{id}")
    fun getSneakerById(@Path("id") id: Int): Call<Sneaker?>?

    companion object {
        const val URL = "http://192.168.0.105:8080/"
    }
}
