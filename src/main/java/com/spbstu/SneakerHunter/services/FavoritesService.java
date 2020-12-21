package com.spbstu.SneakerHunter.services;

import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.UserModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoritesService {
    private UserRepo userRepo;
    private GoodsRepo goodsRepo;

    @Autowired
    public FavoritesService(UserRepo userRepo, GoodsRepo goodsRepo) {
        this.userRepo = userRepo;
        this.goodsRepo = goodsRepo;
    }

    @Transactional
    public UserModel addFavorite(String userId, Long goodsId){
        Optional<UserModel> user = userRepo.findById(userId);
        Optional<GoodsModel> goods = goodsRepo.findById(goodsId);
        user.get().getGoods().add(goods.get());
        return user.get();
    }


}
