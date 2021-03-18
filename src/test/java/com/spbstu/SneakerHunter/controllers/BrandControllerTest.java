package com.spbstu.SneakerHunter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.SneakerHunter.models.BrandModel;
import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.BrandRepo;
import com.spbstu.SneakerHunter.repos.SizeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
class BrandControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BrandRepo brandRepo;

    @Test
    void list() throws Exception {
        when(brandRepo.findAll()).thenReturn(Arrays.asList(
                new BrandModel(1L, "Ashan"),
                new BrandModel(2L, "Asos")
        ));

        mvc.perform(get("/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Ashan", "Asos")));
    }

    @Test
    void getOne() throws Exception {
        when(brandRepo.findById(anyLong())).thenReturn(Optional.of(
                new BrandModel(1L, "Ashan")
        ));

        mvc.perform(get("/brand/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Ashan")));
    }

    @Test
    void create() throws Exception {
        BrandModel brand = new BrandModel( 1L, "Ashan");
        when(brandRepo.save(any())).thenReturn(brand);

        mvc.perform(post("/brand")
                .content(asJsonString(brand))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Ashan")));
    }

    @Test
    void deleteTest() throws Exception {
        BrandModel size = new BrandModel( 1L, "Ashan");
        when(brandRepo.findById(any())).thenReturn(Optional.of(size));
        mvc.perform(
                delete("/brand/1"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}