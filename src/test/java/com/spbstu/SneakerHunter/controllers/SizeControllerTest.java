package com.spbstu.SneakerHunter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.SizeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(SizeController.class)
class SizeControllerTest {
    @InjectMocks
    SizeController sizeController;

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
                new SizeModel("42")
        ));

        mvc.perform(get("/size/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", equalTo("42")));
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
    void deleteTest() throws Exception {
        SizeModel size = new SizeModel( 1L, "43");
        when(sizeRepo.findById(any())).thenReturn(Optional.of(size));
        mvc.perform(
                delete("/size/1"))
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