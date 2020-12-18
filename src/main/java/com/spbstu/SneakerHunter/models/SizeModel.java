package com.spbstu.SneakerHunter.models;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@ToString(of = {"id", "size"})
@EqualsAndHashCode(of = {"id"})
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
    private List<GoodsModel> goods;

    public SizeModel(String size) {
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
}
