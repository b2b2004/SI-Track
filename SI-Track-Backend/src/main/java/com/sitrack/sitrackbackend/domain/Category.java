package com.sitrack.sitrackbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Setter
    @Column(length = 50, nullable = false)
    private String categoryName;

    protected Category() {}

    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category of(Long id, String categoryName){

        return new Category(id, categoryName);
    }

    public static Category of(String categoryName){
        return new Category(categoryName);
    }
}
