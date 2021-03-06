package org.clm.demo.mvc.primiary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    //@Pattern(regexp="^[0-9]",message = "请输入正确的id")
    @Id
    private String Id;

    @NotNull(message = "用户名不能为空")
    @Column
    private String username;

    @NotNull(message = "密码不能为空")
    @Column
    private String password;

    @Transient
    private List<Role> roleList;
    @Transient
    private Set<Menu> menuList;

    public User(@NotNull(message = "用户名不能为空") String username, @NotNull(message = "密码不能为空") String password) {
        this.username = username;
        this.password = password;
    }

    public User(String id, @NotNull(message = "用户名不能为空") String username, @NotNull(message = "密码不能为空") String password) {
        Id = id;
        this.username = username;
        this.password = password;
    }
}
