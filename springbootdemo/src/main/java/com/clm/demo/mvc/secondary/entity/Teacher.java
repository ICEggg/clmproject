package com.clm.demo.mvc.secondary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="teacher")
public class Teacher {
    @Id
    @Column(name="TId")
    private String id;

    @Column(name="Tname")
    private String name;

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
