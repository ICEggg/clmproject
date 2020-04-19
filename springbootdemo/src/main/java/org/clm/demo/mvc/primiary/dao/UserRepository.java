package org.clm.demo.mvc.primiary.dao;

import org.clm.demo.mvc.primiary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Query注解查询
 *      适用于所查询的数据无法通过关键字查询得到结果的查询。这种查询可以摆脱像关键字查询那样的约束，
 *      将查询直接在相应的接口方法中声明，结构更为清晰，这是Spring Data的特有实现。
 *      设置nativeQuery=true 即可以使用原生的SQL进行查询
 *
 * 例子： @Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email = :email")
 *       List<Person> testQueryAnnotationParams2(@Param("email") String email, @Param("lastName") String lastName);
 *
 * @Modifying注解
 *       在@Query注解中编写JPQL实现DELETE和UPDATE操作的时候必须加上@modifying注解，以通知Spring Data 这是一个DELETE或UPDATE操作。
 *       UPDATE或者DELETE操作需要使用事务，此时需要 定义Service层，在Service层的方法上添加事务操作。
 *       注意JPQL不支持INSERT操作。
 */

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    //@Modifying
    @Transactional
    @Query(value="select * from user where username = ?1 and password = ?2", nativeQuery = true)
    User getUserByNameAndPwd(String username,String password);

}
