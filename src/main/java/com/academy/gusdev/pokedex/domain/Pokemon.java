package com.academy.gusdev.pokedex.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Pokemon {

    @Id
    private String id;
    private String name;
    private String category;
    private String skills;
    private Double health;

    public Pokemon(String id, String name, String category, String skills, Double health) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.skills = skills;
        this.health = health;
    }

    public Pokemon() throws CloneNotSupportedException {
        super.clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id) && Objects.equals(name, pokemon.name) && Objects.equals(category, pokemon.category) && Objects.equals(skills, pokemon.skills) && Objects.equals(health, pokemon.health);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, skills, health);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(Double health) {
        this.health = health;
    }
}
