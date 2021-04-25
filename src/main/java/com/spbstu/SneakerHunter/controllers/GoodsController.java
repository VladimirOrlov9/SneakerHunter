package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.GoodsModel;

import com.spbstu.SneakerHunter.repos.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping({"{id}"})
    public ResponseEntity<GoodsModel> getOne(@PathVariable("id") Long id){
        Optional<GoodsModel> goods = goodsRepo.findById(id);
        if (goods.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(goods.get());
    }

    @GetMapping({"name/{name}"})
    public ResponseEntity<GoodsModel> getOne(@PathVariable("name") String name){
        GoodsModel goods = goodsRepo.findByName(name);
        if (goods == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(goods);
    }

    @PostMapping
    public ResponseEntity<GoodsModel> create(@RequestBody GoodsModel goods){
        GoodsModel goodsFromDB = goodsRepo.findByUri(goods.getUri());
        if (goodsFromDB != null)
            return ResponseEntity.status(409).body(goods);
        GoodsModel newGoods = goodsRepo.save(goods);
        return ResponseEntity.status(201).body(newGoods);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<GoodsModel> delete(@PathVariable("id") Long id){
        Optional<GoodsModel> goodsFromDB = goodsRepo.findById(id);
        if (goodsFromDB.isEmpty())
            return ResponseEntity.notFound().build();
        goodsRepo.deleteById(id);
        return ResponseEntity.ok().body(goodsFromDB.get());
    }
}
