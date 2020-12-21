package com.spbstu.SneakerHunter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class UserModel {
    @Id
    private String id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name="goods_user",
            joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="goods_id"))
    @JsonBackReference
    @Nullable
    private List<GoodsModel> goods;

    public UserModel(String id, List<GoodsModel> goods) {
        this.id = id;
        this.goods = goods;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GoodsModel> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsModel> goods) {
        this.goods = goods;
    }
}
