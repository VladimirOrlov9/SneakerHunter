package com.spbstu.SneakerHunter.repos;

import com.spbstu.SneakerHunter.models.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepo extends JpaRepository<ShopModel, Long> {
    ShopModel findByTitle(String title);
}
