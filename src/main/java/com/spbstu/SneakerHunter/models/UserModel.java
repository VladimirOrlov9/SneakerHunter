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
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name="goods_user",
            joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="goods_id"))
    @JsonBackReference
    @Nullable
    private List<GoodsModel> goods;
}
