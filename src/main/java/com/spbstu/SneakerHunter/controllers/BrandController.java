package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.BrandModel;
import com.spbstu.SneakerHunter.repos.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<BrandModel> getOne(@PathVariable("id") Long id){
        Optional<BrandModel> brandFromDB = brandRepo.findById(id);
        if (brandFromDB.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(brandFromDB.get());
    }

    @PostMapping
    public ResponseEntity<BrandModel> create(@RequestBody BrandModel brand){
        BrandModel brandFromDB = brandRepo.findByName(brand.getName());
        if (brandFromDB != null)
            return ResponseEntity.status(409).body(brand);
        BrandModel newBrand = brandRepo.save(brand);
        return ResponseEntity.status(201).body(newBrand);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<BrandModel> delete(@PathVariable("id") Long id){
        Optional<BrandModel> brandFromDB = brandRepo.findById(id);
        if (brandFromDB.isEmpty())
            return ResponseEntity.notFound().build();
        brandRepo.deleteById(id);
        return ResponseEntity.ok().body(brandFromDB.get());
    }
}