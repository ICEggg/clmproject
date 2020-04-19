package org.clm.demo.mvc.primiary.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
public class User {

    @Pattern(regexp="^[0-9]",message = "请输入正确的id")
    @javax.persistence.Id
    private String Id;

    @NotNull(message = "用户名不能为空")
    @Column
    private String username;

    @NotNull(message = "密码不能为空")
    @Column
    private String password;

    public User(String id, String username, String password) {
        Id = id;
        this.username = username;
        this.password = password;
    }

}
