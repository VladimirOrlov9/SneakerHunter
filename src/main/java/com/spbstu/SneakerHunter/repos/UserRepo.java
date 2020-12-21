package com.spbstu.SneakerHunter.repos;

import com.spbstu.SneakerHunter.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, String> {
}
