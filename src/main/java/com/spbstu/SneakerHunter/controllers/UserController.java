package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.UserModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepo userRepo;
    private final GoodsRepo goodsRepo;
    @Autowired
    UserController(UserRepo userRepo, GoodsRepo goodsRepo){
        this.userRepo = userRepo;
        this.goodsRepo = goodsRepo;
    }

    @GetMapping({"{id}"})
    public UserModel getOne(@PathVariable("id") UserModel user){
        return user;
    }

    @PostMapping({"{id}"})
    public Optional<UserModel> create(@PathVariable("id") String id){
        try{
            userRepo.save(new UserModel(id, null));
        }
        catch(Throwable e) {
            e.printStackTrace();
        }
        return userRepo.findById(id);
    }

    @DeleteMapping({"{id}"})
    public void delete(@PathVariable("id") UserModel user){
        userRepo.delete(user);
    }
}