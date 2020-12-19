package com.spbstu.sneakerhunter.server_list;

import com.spbstu.sneakerhunter.server_list.Sneaker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SneakersAPI {
    String URL = "http://192.168.0.105:8080/";

    @GET("sneakers")
    Call<List<Sneaker>> getSneakers();

    @GET("size")
    Call<List<Size>> getSizes();
}
