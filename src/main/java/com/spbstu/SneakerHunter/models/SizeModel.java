package com.spbstu.SneakerHunter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@ToString(of = {"id", "size"})
@EqualsAndHashCode(of = {"id"})
@Data
public class SizeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @NotNull
    @Column(unique = true)
    private String size;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name="goods_size",
            joinColumns=@JoinColumn (name="size_id"),
            inverseJoinColumns=@JoinColumn(name="goods_id"))

    @Nullable
    private List<GoodsModel> goods;

    public SizeModel(String size) {
        this.size = size;
    }

    public SizeModel(Long id, String size) {
        this.id = id;
        this.size = size;
    }

    public SizeModel() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

