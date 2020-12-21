package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.SizeModel;
import com.spbstu.SneakerHunter.models.UserModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.repos.UserRepo;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepo userRepo;
    @Autowired
    UserController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping({"{id}"})
    public UserModel getOne(@PathVariable("id") UserModel user){
        return user;
    }

    //@GetMapping({"{id}/favorites"})
    //public List<SizeModel> list(){
    //    return userRepo.findAll();
    //}

    @PostMapping
    public UserModel create(@RequestBody UserModel user){
        try{
            userRepo.save(user);
        }
        catch(Throwable e) {
            e.printStackTrace();
        };
        return user;
    }

    @DeleteMapping({"{id}"})
    public void delete(@PathVariable("id") UserModel user){
        userRepo.delete(user);
    }
}