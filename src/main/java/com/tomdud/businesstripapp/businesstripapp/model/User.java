package com.tomdud.businesstripapp.businesstripapp.model;

import com.tomdud.businesstripapp.businesstripapp.util.Role;

import java.util.Set;

public class User {

    private long id;
    private String username;
    private String password;

    private Set<Role> roles;

    public User(long id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
