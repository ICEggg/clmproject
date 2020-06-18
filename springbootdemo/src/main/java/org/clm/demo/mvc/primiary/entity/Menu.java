package org.clm.demo.mvc.primiary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu {
    @Id
    private int id;

    @NotNull(message = "菜单名称不能为空")
    @Column(name = "menuName")
    private String menuName;

}
