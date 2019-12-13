package com.clm.demo.mvc.primiary.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class wc_table {

    @Id
    @GeneratedValue
    private Integer id;
    private String word;
    private Integer count;

    public wc_table() {
    }

    public wc_table(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
