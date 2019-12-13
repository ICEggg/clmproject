package com.clm.demo.mvc.primiary.entity;

import javax.persistence.*;

@Entity
public class User {

    @Id
    private String Id;

    @Column
    private String username;

    @Column
    private String password;

    public User(String id, String username, String password) {
        Id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
