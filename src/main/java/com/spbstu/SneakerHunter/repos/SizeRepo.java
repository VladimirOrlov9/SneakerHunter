package com.spbstu.SneakerHunter.repos;

import com.spbstu.SneakerHunter.models.SizeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepo extends JpaRepository<SizeModel, Long> {
    SizeModel findBySize(String size);
}
