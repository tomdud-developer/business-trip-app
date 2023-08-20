package com.tomdud.businesstripapp.model;

import com.tomdud.businesstripapp.util.Role;


public class User {

    private long id;
    private String username;
    private String password;

    private Role role;

    public User(long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }
}
