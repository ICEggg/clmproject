package org.clm.demo.mvc.primiary.dao;

import org.clm.demo.mvc.primiary.entity.Menu;
import org.clm.demo.mvc.primiary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {

    @Query(value = "select a.id,a.path,a.menu_name from  menu a left join role_menu_relation b on a.id = b.menu_id " +
            "left join role c on b.role_id = c.id where b.role_id = ?1",nativeQuery = true)
    Set<Menu> getMenuListByRoleId(int roleId);

}
