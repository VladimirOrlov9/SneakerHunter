package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.BrandModel;
import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
        //return size;
        Optional<BrandModel> brands = brandRepo.findById(id);
        if (!brands.isPresent())
            throw new EntityNotFoundException("id-" + id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(brands.get());
    }

    @PostMapping
    public ResponseEntity<BrandModel> create(@RequestBody BrandModel brand){
        //return brandRepo.save(size);
        BrandModel p = brandRepo.save(brand);
        return ResponseEntity.status(201).body(p);
    }

    @DeleteMapping({"{id}"})
    public void delete(@PathVariable("id") BrandModel brand){
        brandRepo.delete(brand);
    }
}