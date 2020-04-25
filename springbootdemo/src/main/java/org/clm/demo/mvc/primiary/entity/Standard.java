package org.clm.demo.mvc.primiary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clm.demo.mvc.primiary.compositekey.StandardPrimiaryKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StandardPrimiaryKey.class)    //应用联合主键
public class Standard {

    //@GeneratedValue   表示sId为一个自增长字段，也就不需要设置set方法了
    @Id
    private String id;
    @Id
    private String version;
    @Column(name = "name")
    private String name;
}
