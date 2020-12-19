package com.spbstu.SneakerHunter.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Entity
@Table
@ToString(of = {"id", "money", "gender", "uri"})
@EqualsAndHashCode(of = {"id", "category", "size", "brand", "picture", "money", "gender", "uri"})
@Data
@JsonIdentityInfo(
        generator= ObjectIdGenerators.PropertyGenerator.class,
        property="id", scope = GoodsModel.class)
public class GoodsModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name="goods_size",
            joinColumns=@JoinColumn (name="goods_id"),
            inverseJoinColumns=@JoinColumn(name="size_id"))
    @JsonManagedReference
    private List<SizeModel> size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandModel brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private PictureModel picture;

    @NotNull
    private String money;

    @NotNull
    private String gender;

    @NotNull
    private String uri;

//    @NotNull
//    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @Nullable
    private UserModel customer;

    public GoodsModel(CategoryModel category, List<SizeModel> size, BrandModel brand, PictureModel picture,
                      String money, String gender, String uri, UserModel customer) {
        this.category = category;
        this.size = size;
        this.brand = brand;
        this.picture = picture;
        this.money = money;
        this.gender = gender;
        this.uri = uri;
//        this.quantity = quantity;
        this.customer = customer;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public GoodsModel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public List<SizeModel> getSize() {
        return size;
    }

    public void setSize(List<SizeModel> size) {
        this.size = size;
    }

    public BrandModel getBrand() {
        return brand;
    }

    public void setBrand(BrandModel brand) {
        this.brand = brand;
    }

    public PictureModel getPicture() {
        return picture;
    }

    public void setPicture(PictureModel picture) {
        this.picture = picture;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney() { return money; }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
}
