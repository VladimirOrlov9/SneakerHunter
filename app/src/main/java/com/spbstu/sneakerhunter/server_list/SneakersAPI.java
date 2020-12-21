package com.spbstu.sneakerhunter.server_list;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SneakersAPI {
    String URL = "http://192.168.0.105:8080/";

    @GET("sneakers")
    Call<List<Sneaker>> getSneakers();

    @GET("size")
    Call<List<Size>> getSizes();

    @GET("brand")
    Call<List<Brand>> getBrands();

    @GET("sneakers/{id}")
    Call<Sneaker> getSneakerById(@Path("id") int id);
}
