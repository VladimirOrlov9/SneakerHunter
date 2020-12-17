package com.spbstu.SneakerHunter.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@ToString(of = {"id", "name", "email", "gender"})
@EqualsAndHashCode(of = {"id"})
public class UserModel {
    @Id
    private String id;
    private String name;
    private String userpic;
    private String email;
    private String gender;
    private LocalDateTime lastVisit;
}
