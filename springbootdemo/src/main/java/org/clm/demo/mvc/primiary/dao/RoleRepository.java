package org.clm.demo.mvc.primiary.dao;

import org.clm.demo.mvc.primiary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(value = "select c.id,c.role_name from user a left join user_role_relation b on a.id = b.user_id " +
            "left join role c on b.role_id = c.id where a.id = ?1",nativeQuery = true)
    List<Role> getRoleIdByUserId(String userId);

}
