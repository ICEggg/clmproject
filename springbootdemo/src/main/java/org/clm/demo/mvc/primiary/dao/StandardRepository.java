package org.clm.demo.mvc.primiary.dao;

import org.clm.demo.mvc.primiary.compositekey.StandardPrimiaryKey;
import org.clm.demo.mvc.primiary.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 下面注释的方法，放开启动就会报错
 *
 * 不知道为什么
 */

@Repository
public interface StandardRepository extends JpaRepository<Standard, StandardPrimiaryKey> {

    //void addStandard(Standard standard);

    //void delStandard(String id, String version);

    @Modifying
    @Query(value = "update standard set name = ?1 where id=?2",nativeQuery = true)
    void updateStandard(String name , String id);

    //Standard findStandardByIdVersion(String id,String version);

    @Query(value = "select * from standard where id=?1 and name=?2",nativeQuery = true)
    List<Standard> findStandardByIdName(String id, String name);
}
