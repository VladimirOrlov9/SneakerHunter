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
public class CategoryModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    public CategoryModel() {
    }

    public CategoryModel(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CategoryModel(String title) {
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
