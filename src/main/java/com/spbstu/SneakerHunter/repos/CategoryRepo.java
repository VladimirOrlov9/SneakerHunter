package com.spbstu.SneakerHunter.repos;

import com.spbstu.SneakerHunter.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<CategoryModel, Long> {
    CategoryModel findByTitle(String title);
}
