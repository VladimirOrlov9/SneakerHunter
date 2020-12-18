package com.spbstu.SneakerHunter.repos;

import com.spbstu.SneakerHunter.models.PictureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepo extends JpaRepository<PictureModel, Long> {
    PictureModel findByUrl(String url);
}
