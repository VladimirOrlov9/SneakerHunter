package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.SizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping({"id"})
    public SizeModel getOne(@PathVariable("id") SizeModel size){
        return size;
    }

    @PostMapping
    public SizeModel create(@RequestBody SizeModel size){
        return sizeRepo.save(size);
    }

    @DeleteMapping({"id"})
    public void delete(@PathVariable("id") SizeModel size){
        sizeRepo.delete(size);
    }
}
