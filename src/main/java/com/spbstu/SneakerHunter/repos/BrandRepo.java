package com.spbstu.SneakerHunter.repos;

import com.spbstu.SneakerHunter.models.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<BrandModel, Long> {
    BrandModel findByName(String name);
}
