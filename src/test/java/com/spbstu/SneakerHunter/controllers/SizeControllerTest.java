package com.spbstu.SneakerHunter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.SizeModel;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(SizeController.class)
class SizeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SizeRepo sizeRepo;

    @Test
    void list() throws Exception {
        when(sizeRepo.findAll()).thenReturn(Arrays.asList(
                new SizeModel(1L, "42"),
                new SizeModel("38L")
        ));

        mvc.perform(get("/size"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].size", containsInAnyOrder("38L", "42")));
    }

    @Test
    void getOne() throws Exception {
        when(sizeRepo.findById(anyLong())).thenReturn(Optional.of(
                new SizeModel(1L, "42")
        ));

        mvc.perform(get("/size/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", equalTo("42")));
    }

    @Test
    void getNotExistedSize() throws Exception {
        when(sizeRepo.findById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/size/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        SizeModel size = new SizeModel( 1L, "43");
        size.setGoods(new ArrayList<GoodsModel>());
        when(sizeRepo.save(any())).thenReturn(size);

        mvc.perform(post("/size")
                .content(asJsonString(size))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.size", equalTo("43")));
    }

    @Test
    void createExisted() throws Exception {
        SizeModel size = new SizeModel( 1L, "43");
        size.setGoods(new ArrayList<GoodsModel>());

        doReturn(new SizeModel()).when(sizeRepo).findBySize("43");

        verify(sizeRepo, times(0)).save(any(SizeModel.class));

        mvc.perform(post("/size")
                .content(asJsonString(size))
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().is(409));
    }

    @Test
    void deleteTest() throws Exception {
        SizeModel size = new SizeModel( 1L, "43");
        when(sizeRepo.findById(any())).thenReturn(Optional.of(size));
        mvc.perform(
                delete("/size/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNotExistedTest() throws Exception {
        when(sizeRepo.findById(any())).thenReturn(Optional.empty());
        mvc.perform(
                delete("/size/1"))
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