package com.lapakbaju.store.entity.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String icon;
    private String bg;
    private String color;

    @Column(name = "default_active")
    private Boolean defaultActive = false;

    public Category(){}
}
