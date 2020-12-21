package com.spbstu.SneakerHunter.controllers;

import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.UserModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.repos.UserRepo;
import com.spbstu.SneakerHunter.services.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    //@GetMapping({"{id}/favorites"})
    //public List<SizeModel> list(){
    //    return userRepo.findAll();
    //}

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

    @PostMapping(path = "/{userId}/{goodsId}/favorites")
    public UserModel addFavorite(@PathVariable("userId") String userId , @PathVariable("goodsId") Long goodsId) {

        Optional<UserModel> user = userRepo.findById(userId);
        Optional<GoodsModel> goods = goodsRepo.findById(goodsId);
        user.get().getGoods().add(goods.get());
        return user.get();
    }


        @DeleteMapping({"{id}"})
    public void delete(@PathVariable("id") UserModel user){
        userRepo.delete(user);
    }
}