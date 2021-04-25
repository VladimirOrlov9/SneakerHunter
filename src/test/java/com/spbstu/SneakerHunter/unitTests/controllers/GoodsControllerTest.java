package com.spbstu.SneakerHunter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.SneakerHunter.models.*;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@WebMvcTest(GoodsController.class)
@EnableWebMvc
class GoodsControllerTest {

    @MockBean
    private GoodsRepo goodsRepo;

    @Autowired
    private MockMvc mvc;

    @Test
    void list() throws Exception {
        ShopModel shop = new ShopModel(1L, "Asos", "asos.com");
        PictureModel picture = new PictureModel(1L, "asospicture.com");
        GoodsModel goods = new GoodsModel(shop, "crosovok", null, null, picture, "1000",
                "Man", "qwe.com");
        GoodsModel goods1 = new GoodsModel(shop, "valenok", null, null, picture, "2000",
                "Woman", "qwer.com" );

        when(goodsRepo.findAll()).thenReturn(Arrays.asList(
                goods,
                goods1
        ));

        mvc.perform(get("/sneakers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("crosovok", "valenok")));
    }

    @Test
    void getOne() throws Exception {
        ShopModel shop = new ShopModel(1L, "Asos", "asos.com");
        PictureModel picture = new PictureModel(1L, "asospicture.com");
        when(goodsRepo.findById(anyLong())).thenReturn(Optional.of(
                new GoodsModel(1L, shop, "crosovok", null, null, picture, "1000",
                        "Man", "qwe.com")
        ));

        mvc.perform(get("/sneakers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getNotExistedSize() throws Exception {
        when(goodsRepo.findById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/sneakers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        ShopModel shop = new ShopModel(1L, "Asos", "asos.com");
        PictureModel picture = new PictureModel(1L, "asospicture.com");
        GoodsModel goods = new GoodsModel( 1L, shop, "crosovok", null, null, picture, "1000",
                "Man", "qwe.com");
        when(goodsRepo.save(any())).thenReturn(goods);

        mvc.perform(post("/sneakers")
                .content(asJsonString(goods))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("crosovok")));
    }

    @Test
    void createExisted() throws Exception {
        ShopModel shop = new ShopModel(1L, "Asos", "asos.com");
        PictureModel picture = new PictureModel(1L, "asospicture.com");
        GoodsModel goods = new GoodsModel( 1L, shop, "crosovok", null, null, picture, "1000",
                "Man", "qwe.com");

        doReturn(new GoodsModel()).when(goodsRepo).findByUri("qwe.com");

        verify(goodsRepo, times(0)).save(any(GoodsModel.class));

        mvc.perform(post("/sneakers")
                .content(asJsonString(goods))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().is(409));
    }

    @Test
    void deleteTest() throws Exception {
        ShopModel shop = new ShopModel(1L, "Asos", "asos.com");
        PictureModel picture = new PictureModel(1L, "asospicture.com");
        GoodsModel goods = new GoodsModel( 1L, shop, "crosovok", null, null, picture, "1000",
                "Man", "qwe.com");
        when(goodsRepo.findById(any())).thenReturn(Optional.of(goods));
        mvc.perform(
                delete("/sneakers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNotExistedTest() throws Exception {
        when(goodsRepo.findById(any())).thenReturn(Optional.empty());
        mvc.perform(
                delete("/sneakers/1"))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}