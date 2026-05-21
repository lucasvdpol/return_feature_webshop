package com.luxuryproductsholding.api.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    List<CustomUser> customUsers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CustomUser> getCustomUsers() {
        return customUsers;
    }

    public void setCustomUsers(List<CustomUser> customUsers) {
        this.customUsers = customUsers;
    }

    public Role(String email) {this.email = email;}

    public Role() {}
}

