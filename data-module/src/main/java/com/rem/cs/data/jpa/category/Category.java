package com.rem.cs.data.jpa.category;

import com.rem.cs.data.jpa.item.Item;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "_id")
    private String id;

    @Column(name = "_name")
    private String name;

    @Column(name = "_description")
    private String description;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Category parent;

    @ManyToMany(mappedBy = "categories")
    private Set<Item> items = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
