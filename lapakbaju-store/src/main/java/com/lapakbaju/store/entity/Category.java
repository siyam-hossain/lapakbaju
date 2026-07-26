package com.lapakbaju.store.entity;

import jakarta.persistence.*;

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

    public Category(Long id, String name, String icon, String bg, String color, Boolean defaultActive) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.bg = bg;
        this.color = color;
        this.defaultActive = defaultActive;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getDefaultActive() {
        return defaultActive;
    }

    public void setDefaultActive(Boolean defaultActive) {
        this.defaultActive = defaultActive;
    }
}
