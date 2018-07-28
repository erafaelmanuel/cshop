package com.rem.cs.data.jpa.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_role")
public class Role {

    @Id
    @Column(name = "_id")
    private String id;

    @Column(name = "_name")
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)
    private Set<User> users = new HashSet<>();

    public Role() {}

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
}
