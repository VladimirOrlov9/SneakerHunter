package com.spbstu.SneakerHunter.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.SneakerHunter.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-clear-test-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear-test-data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RestApiTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllSizesTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/size"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].size").value("US 10"))
                .andDo(print())
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getExistedSizeTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/size/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.size").value("US 9"))
                .andDo(print())
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getNotExistedSizeTest() throws Exception {
        this.mockMvc.perform(get("/size/2"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getAllSneakersTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/sneakers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2]").doesNotExist())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].gender").value("Men"))
                .andExpect(jsonPath("$[0].name").value("crosovok"))
                .andExpect(jsonPath("$[1].name").value("tapok"))
                .andDo(print())
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getExistedSneakerTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/sneakers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("crosovok"))
                .andDo(print())
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getNotExistedSneakerTest() throws Exception {
        this.mockMvc.perform(get("/sneakers/3"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void postNewSneakerTest() throws Exception {
        List<SizeModel> sizes = new ArrayList<SizeModel>();
        sizes.add(new SizeModel(1L, "US 9"));
        GoodsModel sneaker = new GoodsModel(3L, new ShopModel(1L, "Asos", "https://www.asos.com/us/men/"),
                "valenok", sizes, new BrandModel(1L, "Ashan"), new PictureModel(1L,
                        "images.asos-media.com/products/columbia-vent-aero" +
                                "-sneakers-in-off-white-exclusive-at-asos/23019994-1-darkstoneblack"), "$85.00",
        "Women", "https://valenok.com");

        this.mockMvc.perform(post("/sneakers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sneaker)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        this.mockMvc.perform(get("/sneakers/name/valenok"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("valenok"))
                .andExpect(jsonPath("$.money").value("$85.00"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getAllBrandsTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2]").doesNotExist())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Ashan"))
                .andExpect(jsonPath("$[1].name").value("Adidas"))
                .andDo(print())
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getExistedBrandTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/brand/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("Adidas"))
                .andDo(print())
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getNotExistedBrandTest() throws Exception {
        this.mockMvc.perform(get("/brand/2"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void postNewBrandTest() throws Exception {
        BrandModel brand = new BrandModel(2L, "Nike");
        System.out.println(brand.getId());

        this.mockMvc.perform(post("/brand")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(brand)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
