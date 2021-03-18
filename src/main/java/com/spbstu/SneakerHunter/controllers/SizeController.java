package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.SizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.MediaType;

import java.awt.*;
import java.util.List;

@EnableWebMvc
@RestController
@RequestMapping("size")
public class SizeController {
    private final SizeRepo sizeRepo;
    @Autowired
    SizeController(SizeRepo sizeRepo){
        this.sizeRepo = sizeRepo;
    }

    @GetMapping
    public List<SizeModel> list(){
        return sizeRepo.findAll();
    }

    @GetMapping({"{size}"})
    public SizeModel getOne(@PathVariable("size") SizeModel size){
        return size;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SizeModel> create(@RequestBody SizeModel size){
      //  return sizeRepo.save(size);
        SizeModel p = sizeRepo.save(size);
        return ResponseEntity.status(201).body(p);
    }

    @DeleteMapping({"{id}"})
    public void delete(@PathVariable("id") SizeModel size){
        sizeRepo.delete(size);
    }
}
