package com.spbstu.SneakerHunter.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@ToString(of = {"id", "title"})
@EqualsAndHashCode(of = {"id"})
@Data
public class ShopModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
    @Column(unique = true)
    private String url;

    public ShopModel() {
    }

    public ShopModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public ShopModel(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ShopModel(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
