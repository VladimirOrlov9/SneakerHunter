package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.BrandModel;
import com.spbstu.SneakerHunter.repos.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    private final BrandRepo brandRepo;

    @Autowired
    BrandController(BrandRepo brandRepo){
        this.brandRepo = brandRepo;
    }

    @GetMapping
    public List<BrandModel> list(){
        return brandRepo.findAll();
    }

    @GetMapping({"{id}"})
    public BrandModel getOne(@PathVariable("id") BrandModel size){
        return size;
    }

    @PostMapping
    public BrandModel create(@RequestBody BrandModel size){
        return brandRepo.save(size);
    }

    @DeleteMapping({"{id}"})
    public void delete(@PathVariable("id") BrandModel size){
        brandRepo.delete(size);
    }
}