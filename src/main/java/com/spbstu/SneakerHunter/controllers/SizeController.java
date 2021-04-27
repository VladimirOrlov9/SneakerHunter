package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.repos.SizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.MediaType;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

    @GetMapping({"{id}"})
    public ResponseEntity<SizeModel> getOne(@PathVariable("id") Long id) throws EntityNotFoundException{
        Optional<SizeModel> sizeFromDB = sizeRepo.findById(id);
        if (sizeFromDB.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(sizeFromDB.get());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SizeModel> create(@RequestBody SizeModel size){
        SizeModel sizeFromDB = sizeRepo.findBySize(size.getSize());
        if (sizeFromDB != null)
            return ResponseEntity.status(409).body(size);
        SizeModel newSize = sizeRepo.save(size);
        return ResponseEntity.status(201).body(newSize);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<SizeModel> delete(@PathVariable("id") Long id){
        Optional<SizeModel> sizeFromDB = sizeRepo.findById(id);
        if (sizeFromDB.isEmpty())
            return ResponseEntity.notFound().build();
        sizeRepo.deleteById(id);
        return ResponseEntity.ok().body(sizeFromDB.get());
    }
}
