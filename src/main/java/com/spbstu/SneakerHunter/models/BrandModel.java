package com.spbstu.SneakerHunter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BrandModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    public BrandModel() {
    }

    public BrandModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BrandModel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
