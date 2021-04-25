package com.spbstu.SneakerHunter.integrationTests;

import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.services.AsosApiService.AsosApiDataLoader;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/clear-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear-test-data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DataLoaderTest {
    @Autowired
    AsosApiDataLoader loader;

    @Autowired
    private GoodsRepo goodsRepo;

    @Test
    public void loadDataFromAsosTest() throws Exception {
        List<GoodsModel> sneakers = goodsRepo.findAll();
        assertTrue(sneakers.isEmpty());
        loader.loadData();
        sneakers = goodsRepo.findAll();
        System.out.println(sneakers);
        assertFalse(sneakers.isEmpty());
    }
}
