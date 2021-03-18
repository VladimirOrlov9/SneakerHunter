package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.GoodsModel;

import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
        //return goods;

        Optional<GoodsModel> goods = goodsRepo.findById(id);
        if (!goods.isPresent())
            throw new EntityNotFoundException("id-" + id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(goods.get());
    }

    @PostMapping
    public ResponseEntity<GoodsModel> create(@RequestBody GoodsModel goods){
        //return goodsRepo.save(goods);
        GoodsModel p = goodsRepo.save(goods);
        return ResponseEntity.status(201).body(p);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<GoodsModel> delete(@PathVariable("id") Long id){
        //goodsRepo.delete(goods);
        Optional<GoodsModel> p = goodsRepo.findById(id);
        if (!p.isPresent())
            throw new EntityNotFoundException("id: " + id);

        goodsRepo.deleteById(id);
        return ResponseEntity.ok().body(p.get());
    }
}
