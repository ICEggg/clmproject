package org.clm.demo.mvc.primiary.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Data
//@Valid
public class wc_table {

    @Id
    @GeneratedValue
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotNull(message = "word不能为空")
    private String word;
    @NotNull(message = "count不能为空")
    private Integer count;

    public wc_table() {
    }

    public wc_table(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

}
