package org.clm.demo.mvc.primiary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    @Id
    private int id;

    @NotNull(message = "角色名称不能为空")
    @Column(name = "role_name")
    private String role_name;

    @Transient
    private Set<Menu> menuList;

}
