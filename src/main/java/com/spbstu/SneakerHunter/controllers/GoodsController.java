package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.GoodsModel;

import com.spbstu.SneakerHunter.repos.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sneakers")
public class GoodsController {
    private final GoodsRepo goodsRepo;
    @Autowired
    GoodsController(GoodsRepo goodsRepo){
        this.goodsRepo = goodsRepo;
    }

    @GetMapping
    public List<GoodsModel> list(){
        return goodsRepo.findAll();
    }

    @GetMapping({"id"})
    public GoodsModel getOne(@PathVariable("id") GoodsModel goods){
        return goods;
    }

    @PostMapping
    public GoodsModel create(@RequestBody GoodsModel goods){
        return goodsRepo.save(goods);
    }

    @DeleteMapping({"id"})
    public void delete(@PathVariable("id") GoodsModel goods){
        goodsRepo.delete(goods);
    }
}
