package com.rem.cs.data.jpa.user;

import com.rem.cs.data.jpa.role.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "_id")
    private String id;

    @Column(name = "_name")
    private String name;

    @Column(name = "_email", unique = true)
    private String email;

    @Column(name = "_password")
    private String password;

    @Column(name = "_activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(name = "tbl_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns =
            {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
