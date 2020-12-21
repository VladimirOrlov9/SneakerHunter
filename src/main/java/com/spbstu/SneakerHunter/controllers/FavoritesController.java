package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favorites")
public class FavoritesController {
    private final GoodsRepo goodsRepo;
    private final UserRepo userRepo;
    @Autowired
    FavoritesController(GoodsRepo goodsRepo, UserRepo userRepo){
        this.goodsRepo = goodsRepo;
        this.userRepo = userRepo;
    }



}
